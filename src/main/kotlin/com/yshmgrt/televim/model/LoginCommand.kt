package com.yshmgrt.televim.model
import org.drinkless.tdlib.Client
import org.drinkless.tdlib.TdApi


var autorizationState: TdApi.AuthorizationState? = null

//rewrite to read from config file in future
fun createApiParameters(): TdApi.TdlibParameters{
    val parameters = TdApi.TdlibParameters()
    parameters.apiId = 919672
    parameters.apiHash = "9b85feea20823c6a28ad50d5a22ce3b0"
    parameters.useMessageDatabase = true
    parameters.useSecretChats = true
    parameters.systemLanguageCode = "en"
    parameters.deviceModel = "Desktop"
    parameters.systemVersion = "Unknown"
    parameters.applicationVersion = "1.0"
    parameters.enableStorageOptimizer = true
    return parameters
}

fun updateAutorizationState(newState: TdApi.AuthorizationState, client: Client){
    autorizationState = newState
    when (autorizationState?.constructor){
        TdApi.AuthorizationStateWaitTdlibParameters.CONSTRUCTOR -> {
            val parameters = createApiParameters()
            client.send(TdApi.SetTdlibParameters(parameters),{})
        }
        TdApi.AuthorizationStateWaitEncryptionKey.CONSTRUCTOR -> {
            client.send(TdApi.CheckDatabaseEncryptionKey(),{})
        }
        else -> {
            println(autorizationState?.constructor)
        }
    }
}

class UpdatesHandler: Client.ResultHandler{
    override fun onResult(`object`: TdApi.Object?) {
        println(`object`.toString())
    }

}


fun createClient(): Client {
    System.loadLibrary("tdjni")
    val client = Client.create(null, null, null)

    // test Client.execute
    //UpdatesHandler().onResult(Client.execute(TdApi.GetTextEntities("@telegram /test_command https://telegram.org telegram.me @gif @test")))
    return client
}


