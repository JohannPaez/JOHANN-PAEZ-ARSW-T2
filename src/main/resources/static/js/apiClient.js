var apiClient = (function(){

    return {
        getStatsByCountry: function(name, callback) {
            var promise = $.get({
                url: "/cases/"+ name
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