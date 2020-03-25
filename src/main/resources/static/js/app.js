var app = (function(){

    var map;
    var markers;
    var bounds;
    var marcadores; 


    var crearMapa = function() {
        map = new google.maps.Map(document.getElementById('map'), {
            center: {lat: -34.397, lng: 150.644},
            zoom: 8
        });
        plotMarkers(marcadores);
    }


    var propiedades = function(error, json) {
        if (error != null) {
            return;
        }

        obtenerPropiedades(json);
        crearMapa();
    }

    var obtenerPropiedades = function(json) {
        marcadores = [];
        json.forEach(function(country) {			
            var coordenadasJson = {lat:country.latlng[0], lng:country.latlng[1]};
            marcadores.push(coordenadasJson);
        });
    };
    
    function plotMarkers(m) {
        markers = [];
        bounds = new google.maps.LatLngBounds();

        m.forEach(function (marker) {
            var position = new google.maps.LatLng(marker.lat, marker.lng);

            markers.push(
            new google.maps.Marker({
                position: position,
                map: map,
                animation: google.maps.Animation.DROP
            })
            );

            bounds.extend(position);
        });

        map.fitBounds(bounds);
    }

    var tableR = function(error, deaths, infected, cured) {
        if (error != null) {
            alert("ENTRO ALERT");
            return;
        }
        var StringTableRight = tableRight(deaths, infected, cured);
        var StringTableRight2 = tableRight2(deaths, infected, cured);
        $("#idTableRight2").html(StringTableRight2);
        $("#idTableRight").html(StringTableRight);
        
    }

    var tableRight2 = function(deaths, infected, cured) {
          var tabla = "<table class='table table-bordered table-dark' style = 'width:200px; text-align: center;'" +
						"<thead>" +
							"<tr>" +
								"<th scope='col'> Birth Date </th>" +
							"</tr>" +
						"</thead>" +
                        "<tbody>";
     
            tabla += "<tr>" +
                        "<td>"+  "Num Deaths" + "</td>" +
                    "</tr>";            
            tabla += "<tr>" +
                        "<td>" + "Num Infected" + "</td>" +
                    "</tr>";                    
            tabla += "<tr>" +
                        "<td>"+ "Num Cured" + "</td>" +
                    "</tr>";
        
        tabla += "</tbody> </table>";
        return tabla;
    }

    var tableRight = function(deaths, infected, cured) {
    
        var tabla = "<table class='table table-bordered table-dark' style = 'width:200px; text-align: center;'" +
						"<thead>" +
							"<tr>" +
								"<th scope='col'> Birth Date </th>" +
							"</tr>" +
						"</thead>" +
                        "<tbody>";
     
            tabla += "<tr>" +
                        "<td>"+  deaths + "</td>" +
                    "</tr>";            
            tabla += "<tr>" +
                        "<td>" + infected + "</td>" +
                    "</tr>";                    
            tabla += "<tr>" +
                        "<td>"+ cured + "</td>" +
                    "</tr>";
        
        tabla += "</tbody> </table>";
        return tabla;
    }


    
    var tableAndMap = function(error, json) {
        if (error != null) {
            return;
        }

        var StringTable = table(json);
        $("#idTable").html(StringTable);
        //crearMapa();
    }

    var table = function(json) {
        var tabla = "<center> <table class='table table-bordered table-dark' style = 'width:800px; text-align: center;'" +
                    "<thead>" +
                        "<tr>" +
                            "<th scope='col'> Country </th>" +
                            "<th scope='col'> Num Deaths </th>" +
                            "<th scope='col'> Num Infected </th>" +
                            "<th scope='col'> Num Cured </th>" +
                        "</tr>" +
                    "</thead>" +
                    "<tbody>";
        marcadores = [];
        for (var country in json) {                
            //var coordenadas = {lat:airport.location.latitude, lng: airport.location.longitude}
            //marcadores.push(coordenadas);
            var string = String(json[country]);
            var quitar = string.replace("[","");
            quitar = quitar.replace("]","");        
            var final = String(quitar);
            var datosCountry = JSON.parse(final);
            //alert(datosCountry.deaths + " " + datosCountry.infected + " " + datosCountry.cured);
            var infected = String(datosCountry.infected);
        
            tabla += "<tr>" +
                    "<td>" + String(country) + "</td>" +
                    "<td>" + datosCountry.deaths + "</td>" +
                    "<td>" + datosCountry.infected + "</td>" +
                    "<td>" + datosCountry.cured + "</td>" +
                "</tr>";
            
        }
        
        tabla += "</tbody> </table> </center>";
        return tabla;
    }


    return {

        
        getStatsByCountry: function(name) {
            
            apiClient.getStatsByCountry(name, tableR);
            app.getPropiedades(name);

        },
        
        getAll: function() {
            apiClient.getAll(tableAndMap);
        },

        getPropiedades: function(name) {
            apiClient.getPropiedades(name, propiedades);
        }

    }

})();