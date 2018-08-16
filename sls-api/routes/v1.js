'use strict';
var express = require('express');
var router = express.Router();

const staffController = require('../controllers/staffController');

/* GET home page. */
router.get('/', (req, res, next) => {
    res.render('index', { title: 'SLS API' });
});

/* Test the server */
router.get('/ping', (req, res, next) => {
    res.send('API Wokring');
});

/* STAFF */
router.post("/staff/register", staffController.register);
router.post("/staff/authenticate", staffController.authenticate);

module.exports = router;