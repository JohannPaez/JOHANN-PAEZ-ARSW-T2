var apiClient = (function(){

    return {
        getStatsByCountry: function(name, callback) {
            var promise = $.get({
                url: "/cases/"+ name
            });

            promise.then(function(data) {
                console.log("DATA ------------------------ " + data);

                var datos = data.replace("[","");
                datosFinal = datos.replace("]","");
                datosFinal = JSON.parse(datosFinal);
                var deaths = datosFinal.deaths;
                var infected = datosFinal.infected;                
                var cured = datosFinal.cured;      
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
                //console.log("DATA ------------------------ " + data);                
                callback(null, JSON.parse(data))
            }, function(error) {
                callback(error, null);
            });
        },
        getPropiedades: function(name, callback) {
            console.log("STRING URL" + name);
            var promise = $.get({
                url: "/cases/"+ name + "/" + name
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