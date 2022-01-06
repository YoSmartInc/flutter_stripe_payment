package de.jonasbark.stripepayment

import androidx.annotation.NonNull
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReadableMap
import com.gettipsi.stripe.StripeModule
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import java.io.Serializable

class StripePaymentPlugin: FlutterPlugin,
    MethodChannel.MethodCallHandler,ActivityAware {
    private var channel: MethodChannel? = null
    private var stripeModule: StripeModule? = null

    override fun onMethodCall(call: MethodCall,result: MethodChannel.Result) {
        when (call.method) {
            "setOptions" -> stripeModule?.init(
                ReadableMap(call.argument("options")),
                ReadableMap(call.argument("errorCodes"))
            )
            "setStripeAccount" -> stripeModule?.setStripeAccount(
                call.argument("stripeAccount")
            )
            "deviceSupportsAndroidPay" -> stripeModule?.deviceSupportsAndroidPay(Promise(result));
            "canMakeAndroidPayPayments" -> stripeModule?.canMakeAndroidPayPayments(Promise(result));
            "paymentRequestWithAndroidPay" -> stripeModule?.paymentRequestWithAndroidPay(
                ReadableMap(call.arguments as Map<String, Any>),
                Promise(result)
            )
            "paymentRequestWithCardForm" -> stripeModule?.paymentRequestWithCardForm(
                ReadableMap(call.arguments as Map<String, Any>),
                Promise(result)
            )
            "createTokenWithCard" -> stripeModule?.createTokenWithCard(
                ReadableMap(call.arguments as Map<String, Any>),
                Promise(result)
            )
            "createTokenWithBankAccount" -> stripeModule?.createTokenWithBankAccount(
                ReadableMap(call.arguments as Map<String, Any>),
                Promise(result)
            )
            "createSourceWithParams" -> stripeModule?.createSourceWithParams(
                ReadableMap(call.arguments as Map<String, Any>),
                Promise(result)
            )
            "createPaymentMethod" -> stripeModule?.createPaymentMethod(
                ReadableMap(call.arguments as Map<String, Any>),
                Promise(result)
            )
            "authenticatePaymentIntent" -> stripeModule?.authenticatePaymentIntent(
                ReadableMap(call.arguments as Map<String, Any>),
                Promise(result)
            )
            "confirmPaymentIntent" -> stripeModule?.confirmPaymentIntent(
                ReadableMap(call.arguments as Map<String, Any>),
                Promise(result)
            )
            "authenticateSetupIntent" -> stripeModule?.authenticateSetupIntent(
                ReadableMap(call.arguments as Map<String, Any>),
                Promise(result)
            )
            "confirmSetupIntent" -> stripeModule?.confirmSetupIntent(
                ReadableMap(call.arguments as Map<String, Any>),
                Promise(result)
            )
        }
    }


    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(binding.binaryMessenger, "stripe_payment")
        channel?.setMethodCallHandler(this)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel?.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        if(stripeModule == null){
            this.stripeModule = StripeModule(binding, binding.activity);
        }
        TODO("Not yet implemented")
    }

    override fun onDetachedFromActivityForConfigChanges() {
        TODO("Not yet implemented")
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        TODO("Not yet implemented")
    }

    override fun onDetachedFromActivity() {
        TODO("Not yet implemented")
    }
}
