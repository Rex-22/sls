'use strict';
var express = require('express');
var router = express.Router();

const staffController = require('../controllers/staffController');
const leadController = require('../controllers/leadController');
const companieController = require('../controllers/companieController');

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

/* LEAD */
router.get("/leads/:id", leadController.get)
router.get("/leads", leadController.list)
router.post("/leads", leadController.create)
router.put("/leads/:id", leadController.update)
router.delete("/leads/:id", leadController.delete)

/* COMPANIE */
router.get("/companies/:id", companieController.get)
router.get("/companies", companieController.list)
router.post("/companies", companieController.create)
router.put("/companies/:id", companieController.update)
router.delete("/companies/:id", companieController.delete)

module.exports = router;