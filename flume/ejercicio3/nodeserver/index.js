var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var kafka = require('kafka-node'), Consumer = kafka.Consumer, client = new kafka.Client(), consumer = new Consumer(
		client, [ {
			topic : 'prueba2',
			partition : 0
		}], {
			autoCommit : false
		});

app.get('/', function(req, res) {
	res.sendFile(__dirname + '/index.html');
});

io.on('connection', function(socket) {
	consumer.on('message', function (message) {
		io.emit('chart value', message.value);
	});	
});

http.listen(3000, function() {
	console.log('listening on *:3000');
});
