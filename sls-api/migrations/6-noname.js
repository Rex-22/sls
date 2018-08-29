'use strict';

var Sequelize = require('sequelize');

/**
 * Actions summary:
 *
 * removeColumn "dateAdded" from table "leads"
 * removeColumn "lastName" from table "leads"
 * removeColumn "firstName" from table "leads"
 * addColumn "date_added" to table "leads"
 * addColumn "last_name" to table "leads"
 * addColumn "first_name" to table "leads"
 *
 **/

var info = {
    "revision": 6,
    "name": "noname",
    "created": "2018-08-29T20:52:56.032Z",
    "comment": ""
};

var migrationCommands = [{
        fn: "removeColumn",
        params: ["leads", "dateAdded"]
    },
    {
        fn: "removeColumn",
        params: ["leads", "lastName"]
    },
    {
        fn: "removeColumn",
        params: ["leads", "firstName"]
    },
    {
        fn: "addColumn",
        params: [
            "leads",
            "date_added",
            {
                "type": Sequelize.DATE
            }
        ]
    },
    {
        fn: "addColumn",
        params: [
            "leads",
            "last_name",
            {
                "type": Sequelize.STRING
            }
        ]
    },
    {
        fn: "addColumn",
        params: [
            "leads",
            "first_name",
            {
                "type": Sequelize.STRING
            }
        ]
    }
];

module.exports = {
    pos: 0,
    up: function(queryInterface, Sequelize)
    {
        var index = this.pos;
        return new Promise(function(resolve, reject) {
            function next() {
                if (index < migrationCommands.length)
                {
                    let command = migrationCommands[index];
                    console.log("[#"+index+"] execute: " + command.fn);
                    index++;
                    queryInterface[command.fn].apply(queryInterface, command.params).then(next, reject);
                }
                else
                    resolve();
            }
            next();
        });
    },
    info: info
};
