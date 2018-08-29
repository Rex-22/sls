'use strict';

var Sequelize = require('sequelize');

/**
 * Actions summary:
 *
 * removeColumn "compId" from table "leads"
 * changeColumn "cell" on table "leads"
 * changeColumn "tell" on table "leads"
 *
 **/

var info = {
    "revision": 4,
    "name": "noname",
    "created": "2018-08-29T20:39:33.201Z",
    "comment": ""
};

var migrationCommands = [{
        fn: "removeColumn",
        params: ["leads", "compId"]
    },
    {
        fn: "changeColumn",
        params: [
            "leads",
            "cell",
            {
                "type": Sequelize.STRING,
                "validate": {
                    "isNumeric": {
                        "msg": "Cell number can only be numeric"
                    }
                }
            }
        ]
    },
    {
        fn: "changeColumn",
        params: [
            "leads",
            "tell",
            {
                "type": Sequelize.STRING,
                "validate": {
                    "isNumeric": {
                        "msg": "Tell number can only be numeric"
                    }
                }
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
