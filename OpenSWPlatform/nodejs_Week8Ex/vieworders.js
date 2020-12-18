var fs = require("fs");
const url = require("url");
const path = url.parse(__dirname, true).pathname;

if (path == __dirname) {
    fs.readFile(__dirname + '/vieworders.html', (err, data) => {
        if (err) {
          return console.error("No orders pending. Please try again later.");

        }else{
            console.log(data.toString());
        }
    });

    fs.readFile(__dirname + '/orders.txt', function(err,data){
        if(err){
            return console.error("No orders pending. Please try again later.");
        }else{
            console.log(data.toString());
        }
    });

}