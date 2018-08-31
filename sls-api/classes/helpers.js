var _ = require("lodash");
var FCM = require("fcm-push");
var serverKey =
  "AAAAqnGdgzo:APA91bF--ra7eJxSHXXnsnQXjtIxW0yj2HNumOMxKlnX_flJj5EWisShdZARG49yqdToSzqYLo7AMqoU7sQqegk5iabF6NSEc3Sf0mzdUNFgPIkHyx6FYmw1sbSzAe72I-CifptI8BM-0gR3xJcn9jVKX6vb9JPlfg";
var fcm = new FCM(serverKey);

var timestamp = ["created_at", "deleted_at", "updated_at"];
(exports.time_stamps = timestamp),
(exports.PrettyPrint = function (err) {
  var errors = [];
  for (var i = 0; i < err.errors.length; i++) {
    errors.push(err.errors[i].message);
  }
  return errors;
}),
(exports.SendPushNotification = function (fcm_token, body, title) {
  var message = {
    to: fcm_token, // required fill with device token or topics
    notification: {
      title: title,
      body: body
    }
  };
  //promise style
  fcm.send(message);
});