var apiClient = (function(){

    return {
        getStatsByCountry: function(name, callback) {
            var promise = $.get({
                url: "/cases/"+ name
            });

            promise.then(function(data) {
                console.log("DATA ------------------------ " + data);
                data = JSON.parse(data);
                var deaths = data.deaths;
                var infected = data.infected;                
                var cured = data.cured;      
                console.log(deaths + " " + infected + " " + cured);                          
                callback(null, deaths, infected, cured);
            }, function(error) {
                callback(error, null);
            });
        },
        getAll: function(callback) {
            var promise = $.get({
                url: "/cases"
            });
            promise.then(function(data) {
                console.log("DATA ------------------------ " + data);                
                callback(null, JSON.parse(data))
            }, function(error) {
                callback(error, null);
            });
        }
    }

})();