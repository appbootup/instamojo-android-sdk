# Instamojo Android SDK 

Table of Contents
=================

   * [Overview](#overview)
   * [Payment flow using SDK](#payment-flow-using-sdk)
   * [Sample Application](#sample-application)
   * [SDK Installation](#sdk-installation)
     * [SDK Dependency](#sdk-dependency)
     * [SDK Permissions](#sdk-permissions)
     * [Proguard rules](#proguard-rules)
   * [Fetch Order ID from backend server](#fetch-order-id-from-backend-server)
   * [Simple Integration](#simple-integration)
     * [Initializing SDK](#initializing-sdk)
     * [Initiating Payment](#initiating-payment)
     * [Receiving response from the SDK](#receiving-response-from-the-sdk)
   * [Using Custom Created UI](#using-custom-created-ui)
    
##***Note:SDK currently does not support MarketPlace Integration. MarketPlace API Documentation is available [here](https://docs.instamojo.com/v2/docs)***

##Overview
This SDK allows you to integrate payments via Instamojo into your Android app. It currently supports following modes of payments:

1. Credit / Debit Cards
2. EMI 
3. Net Banking
4. Wallets

This SDK also comes pre-integrated with Juspay Safe Browser, which makes payments on mobile easier, resulting in faster transaction time and improvement in conversions.

1. 1-click OTP: Auto-processing Bank SMS OTP for 1-Click experience.
2. Network optimizations: Smart 2G connection handling to reduce page load times.
3. Input & Keyboard Enhancements: Displays right keyboard with password viewing option.
4. Smooth User Experience: Aids the natural flow of users with features like Automatic Focus, Scroll/Zoom, Navigation buttons.

##Payment flow using SDK
The section describes how the payment flow probably looks like, when you integrate with this SDK. Note that, this is just for reference and you are free to make changes to this flow that works well for you.

- When the buyer clicks on Buy button, your app makes a call to your backend to initiate a transaction in your system.
- Your backend systems creates a payment order and returns the order ID to the app. See sample backend.
- The app then initiates a payment via Instamojo using the OrderID.
- If OrderID is valid, the user is shown the payment modes, which will take him via the payment process as per mode selected.
- Once a payment is completed, a callback is called in your Android app with your transaction_id and the payment status.
- Your app shows the success / failure screen based on the payment status.
- For more details on the payment, your app makes a request to your backend servers with the transaction_id, which then returns the details after fetching the same from Instamojo servers.


## Sample Application 
A sample app that uses the latest SDK code is provided under `/sample-app` folder. You can either use it as a base for your project or have a look at the integration in action.
Check out the documentation of the Sample App [here](https://github.com/Instamojo/instamojo-android-sdk/tree/master/sample-app/Readme.md).

## SDK Installation
### SDK Dependency
The SDK currently supports Android Version >= ICS 4.0.3 (15). Just add the following to your application’s `build.gradle` file, inside the dependencies section.
```groovy
repositories {
    mavenCentral()
    maven {
        url "https://s3-ap-southeast-1.amazonaws.com/godel-release/godel/"
    }
}

dependencies {
    compile 'com.instamojo:android-sdk:+'
}

```

### SDK Permissions
The following are the minimum set of permissions required by the SDK. Add the following set of permissions in the application’s manifest file above the `<application>` tag.
```xml
//General permissions 
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

//required for Juspay to read the OTP from the SMS sent to the device
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.READ_SMS" />
<uses-permission android:name="android.permission.RECEIVE_SMS" />
```

### Proguard rules
If you are using Proguard for code obfuscation, add following rules in the proguard configuration file `proguard-rules.pro`
```
# Rules for Instamojo SDK
-keep class com.instamojo.android.**{*;}
```

## Fetch Order ID from backend server
For initiating a payment, you should first create a payment order in your backend server and send the Order ID to the app.
Please check this [documentation](https://github.com/Instamojo/android-sdk-sample-app/blob/master/sample-sdk-server/Readme.md) 
on how to create an order on backend server using client credentials.


## Simple Integration
### Initializing SDK
Add the following code snippet inside the `onCreate()` method of that custom application class.
```Java
    @Override
        public void onCreate() {
            super.onCreate();
            Instamojo.getInstance().initialize(this, Instamojo.Environment.TEST);
            ...
        }
```

### Initiating Payment
Once the SDK is initialized, you can simply call `initiatePayment` with the `orderID` and a the callback instance
```Java
    Instamojo.getInstance().initiatePayment(this, orderID, this);
```

### Receiving response from the SDK
Once the payment is initiated from your activity, it can receive the various SDK responses through the following callback methods

1. Payment through SDK is complete. The payment can be either a success or a failure
```java
@Override
public void onInstamojoPaymentComplete(String orderID, String transactionID, String paymentID, String paymentStatus) {
    Log.d(TAG, "Payment complete. Order ID: " + orderID + ", Transaction ID: " + transactionID
            + ", Payment ID:" + paymentID + ", Status: " + paymentStatus);
}
```

2. Payment is cancelled by the user
```java
@Override
public void onPaymentCancelled() {
    Log.d(TAG, "Payment cancelled");
}
```

3. There was a error while initiating a payment (eg: the orderID is invalid)
```java
@Override
public void onInitiatePaymentFailure(String errorMessage) {
    Log.d(TAG, "Initiate payment failed");
    showToast("Initiating payment failed. Error: " + errorMessage);
}
```

## Using Custom Created UI
We know that every application is unique. If you choose to create your own UI to collect Payment information, SDK has necessary APIs to achieve this.
Use `CustomUIActivity` activity, which uses SDK APIs to collect Payment Information, to extend and modify as per your needs.

## Integrate the SDK using DevsupportAI (DEPRECATED and uses SDK version 1.2.6)
Integration documentation for SDK v1.2.6 using DevsupportAI [here](https://docs.instamojo.com/v1.1/page/devsupport-ai-android-integration).

## I have queries regarding the SDK Integration
If this documentation does not answer any specific question(s) pertaining to SDK integration,
please shoot out a mail to our [support](mailto:support@instamojo.com). We will respond ASAP.
