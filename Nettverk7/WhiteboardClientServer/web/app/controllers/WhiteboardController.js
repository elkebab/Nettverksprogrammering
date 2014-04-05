app.controller('WhiteboardController', function($scope, daoWhiteboard) {
  var canvas = document.getElementById('whiteboard');
  var context = canvas.getContext('2d');
  var chatVindu = document.getElementById('chatVindu');

  var mouseDown = 0;
  var lastX;
  var lastY;

  $scope.colors = [
    {name: 'red', value: '#FF0000'},
    {name: 'green', value: '#00FF00'},
    {name: 'blue', value: '#0000FF'}
  ];
  $scope.color = $scope.colors[0];

  daoWhiteboard.setStatusCallback(function(status) {
    if (status == 0) {
      $scope.status = "Not connected";
    }
    else if (status == 1) {
      $scope.status = "Connected";
    }
    else {
      $scope.status = "Connection error";
    }
  });


  daoWhiteboard.onmessage(function(object) {
      if ("line" in object) {
      drawLine(object.line.x1, object.line.y1, object.line.x2, object.line.y2, object.line.color);
      $scope.remotePainter = object.painter;
    }
    
    chatVindu.value += object.chat.melding + "\n";
  });

  var drawLine = function(x1, y1, x2, y2, color) {
    context.lineWidth = 2;
    context.strokeStyle = color;
    context.beginPath();
    context.moveTo(x1, y1);
    context.lineTo(x2, y2);
    context.closePath();
    context.stroke();
  };

  $scope.mouseMove = function($event) {
    if (mouseDown > 0) {
      var x = $event.offsetX;
      var y = $event.offsetY;

      drawLine(lastX, lastY, x, y, $scope.color.value);

      daoWhiteboard.send({
        "line": {color: $scope.color.value, "x1": lastX, "y1": lastY, "x2": x, "y2": y}
      });

      lastX = x;
      lastY = y;
    }
  };
  $scope.mouseDown = function($event) {
    mouseDown = 1;
    lastX = $event.offsetX;
    lastY = $event.offsetY;
  };
  $scope.mouseUp = function() {
    mouseDown = 0;
  };
  
  var sendMelding = function(txt) {
    chatVindu.value += txt + "\n";
  };
  
  $scope.mouseClick = function($event) {
    var name = $scope.name;
    var msg = $scope.msg;
    var txt = name+": "+msg;
    
    sendMelding(txt);
    
    daoWhiteboard.send({
        "chat": {"melding": txt}
    });
  };
});
