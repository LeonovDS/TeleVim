package com.yshmgrt.televim.model
import org.drinkless.tdlib.Client
import org.drinkless.tdlib.TdApi
import java.io.IOError
import java.io.IOException


var autorizationState: TdApi.AuthorizationState? = null
var client:Client? = null
//rewrite to read from config file in future
fun createApiParameters(): TdApi.TdlibParameters{
    val parameters = TdApi.TdlibParameters()
    parameters.apiId = 919672
    parameters.apiHash = "9b85feea20823c6a28ad50d5a22ce3b0"
    parameters.useMessageDatabase = true
    parameters.useSecretChats = true
    parameters.systemLanguageCode = "en"
    parameters.deviceModel = "Desktop"
    parameters.databaseDirectory = "tdlib"
    parameters.systemVersion = "Unknown"
    parameters.applicationVersion = "1.0"
    parameters.enableStorageOptimizer = true
    return parameters
}

class AuthorisationRequestHandler(private val client: Client, private val getter: ValuesGetter):Client.ResultHandler{

    override fun onResult(`object`: TdApi.Object?) {
        when (`object`?.getConstructor()) {
            TdApi.Error.CONSTRUCTOR -> {
                System.err.println("Receive an error:\n$`object`")
                updateAutorizationState(null, client, getter) // repeat last action
            }
            TdApi.Ok.CONSTRUCTOR -> {
            }
            else -> System.err.println("Receive wrong response from TDLib:\n$`object`")
        }
    }

}

interface ValuesGetter{
    fun getPhoneNumber()
    fun getCode()
    fun onReady()
    fun getRegistration()
}

fun updateAutorizationState(newState: TdApi.AuthorizationState?, client: Client, getter: ValuesGetter){
    if (newState==null) return
    autorizationState = newState
    when (autorizationState){
        is TdApi.AuthorizationStateWaitTdlibParameters -> {
            val parameters = createApiParameters()
            client.send(TdApi.SetTdlibParameters(parameters), AuthorisationRequestHandler(client, getter))
        }
        is TdApi.AuthorizationStateWaitEncryptionKey -> {
            client.send(TdApi.CheckDatabaseEncryptionKey(), AuthorisationRequestHandler(client, getter))
        }
        is TdApi.AuthorizationStateWaitPhoneNumber->{getter.getPhoneNumber()

        }
        is TdApi.AuthorizationStateWaitCode->{
            getter.getCode()
        }
        is TdApi.AuthorizationStateReady->{
            getter.onReady()
        }
        is TdApi.AuthorizationStateWaitRegistration -> {
            getter.getRegistration()
        }
        else -> {
            System.err.println("Login pattern not supported: $autorizationState")
        }
    }
}

fun register(client: Client, fname:String, lname:String, getter: ValuesGetter){
    client.send(TdApi.RegisterUser(fname, lname), AuthorisationRequestHandler(client,getter))
}


fun sendPhone(client: Client, phone:String, getter: ValuesGetter){
    client.send(TdApi.SetAuthenticationPhoneNumber(phone,null), AuthorisationRequestHandler(client, getter))
}

fun sendCode(client: Client, code:String, getter: ValuesGetter){
    client.send(TdApi.CheckAuthenticationCode(code), AuthorisationRequestHandler(client, getter))
}
fun sendLogout(client: Client){
    client.send(TdApi.LogOut()){}
}

class UpdatesHandler(val getter: ValuesGetter): Client.ResultHandler{
    override fun onResult(`object`: TdApi.Object?) {
        println(`object`.toString())
        when(`object`?.constructor) {
            TdApi.UpdateAuthorizationState.CONSTRUCTOR -> {

                updateAutorizationState((`object` as TdApi.UpdateAuthorizationState).authorizationState, client!!, getter)
            }
        }
    }

}

class ExceptionHandler: Client.ExceptionHandler{
    override fun onException(e: Throwable?) {
        println(e?.message)
    }

}


fun createClient(getter: ValuesGetter): Client {
    System.loadLibrary("tdjni")
    client = Client.create(UpdatesHandler(getter), ExceptionHandler(), ExceptionHandler())
    return client!!
}


