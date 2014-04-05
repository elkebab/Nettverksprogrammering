app.service('daoWhiteboard', function($rootScope) {
  var wsUri = "ws://" + document.location.host + document.location.pathname + "whiteboardendpoint";

  var pendingRequests = [];

  var statusCallback;

  var ws = new WebSocket(wsUri);

  ws.onopen = function() {
    for (var i = 0; i < pendingRequests.length; i++) {
      ws.send(pendingRequests[i]);
    }
    pendingRequests = [];
    $rootScope.$apply(callStatusCallback());
  };

  ws.onerror = function() {
    $rootScope.$apply(callStatusCallback());
  };

  ws.onclose = function() {
    $rootScope.$apply(callStatusCallback());
  };

  //(this.) public method. Safest: call these methods only from outside of class
  //("this" may change depending on the function that is parsed, though "this" 
  //can be stored in the same namespace before parsing such functions:
  //"var self=this", and then use "self." inside these functions)
  //Additionally, if used within class, make sure the function is parsed before
  //it is called (place function before the calls). 
  this.setStatusCallback = function(callback) {
    statusCallback = callback;
    callStatusCallback();
  };

  //(var ) private-only method, only callable from within class (same namespace)
  //Do not use function callStatusCallback() {..}
  //That would parse the function at parse-time instead of at run-time
  //(we do not want the client to parse unused singleton-code, 
  //since in large projects most pages, and thus many services, 
  //will never be used in one session)
  var callStatusCallback = function() {
    if (typeof (statusCallback) == 'function') {
      statusCallback(ws.readyState);
    }
  };

  this.onmessage = function(onmessageCallback) {
    ws.onmessage = function(evt) {
      if (typeof (onmessageCallback) == 'function') {
        object = JSON.parse(evt.data);
        $rootScope.$apply(onmessageCallback(object));
      }
    };
  };

  var send = function(request) {
    if (ws.readyState == 1) {
      ws.send(request);
    }
    else {
      pendingRequests.push(request);
    }
  };

  this.send = function(object) {
    send(JSON.stringify(object));
  };
});
