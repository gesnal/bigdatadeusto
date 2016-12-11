var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var kafkaPrueba = require('kafka-node'), ConsumerPrueba = kafkaPrueba.Consumer, clientPrueba = new kafkaPrueba.Client(), consumerPrueba = new ConsumerPrueba(
		clientPrueba, [ {
			topic : 'prueba',
			partition : 0
		}], {
			autoCommit : false
		});

var kafkaResult = require('kafka-node'), ConsumerResult = kafkaResult.Consumer, clientResult = new kafkaResult.Client(), consumerResult = new ConsumerResult(
		clientResult, [ {
			topic : 'result',
			partition : 0
		}], {
			autoCommit : false
		});

app.get('/', function(req, res) {
	res.sendFile(__dirname + '/index.html');
});

io.on('connection', function(socket) {
	consumerPrueba.on('message', function (message) {
		io.emit('chart value', message.value);
	});
	consumerResult.on('message', function (message) {
		io.emit('result', message.value);
	});		
});

http.listen(3000, function() {
	console.log('listening on *:3000');
});
