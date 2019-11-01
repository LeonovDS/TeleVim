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
    fun getPhoneNumber():String
    fun getCode():String
    fun onReady()
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
        is TdApi.AuthorizationStateWaitPhoneNumber->{
            client.send(TdApi.SetAuthenticationPhoneNumber(getter.getPhoneNumber(),null), AuthorisationRequestHandler(client, getter))
        }
        is TdApi.AuthorizationStateWaitCode->{
            client.send(TdApi.CheckAuthenticationCode(getter.getCode()),AuthorisationRequestHandler(client, getter))
        }
        is TdApi.AuthorizationStateReady->{
            getter.onReady()
        }
        else -> {
            println(autorizationState?.constructor)
        }
    }
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


