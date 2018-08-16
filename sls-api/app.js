'use strict';
var createError = require('http-errors');
var express = require('express');
var path = require('path');
var bodyParser = require('body-parser');
var acl = require('acl');
var config = require(__dirname + '/config/environment.json')[env];

var env = process.env.NODE_ENV || 'development';
var authorization = require('./middleware/authorization');
var models = require('./models');

var app = express();

// Access Control List used for authentication
acl = new acl(new acl.memoryBackend());
authorization.initialize(acl);
var authentication = require('./middleware/authentication')(acl, models);

// Configuration
global.EnvironmentConfig = config;

// Routes
var v1 = require('./routes/v1');

// View engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'pug');

// Authentication middleware
app.use('*', authentication.authenticate);

// App dependancies
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
//Normal App routes
app.use('/v1', v1);

// Catch 404 and forward to error handler
app.use(function(req, res, next) {
    next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};
  
  // render the error page
  res.status(err.status || 500);
  res.render('error');
});
  
module.exports = app;