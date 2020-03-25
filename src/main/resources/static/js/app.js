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
            if (json.hasOwnProperty(country)) {
                //alert("La clave es " + country+ " y el valor es " + json[country]);                
                
                //console.log(json.clave);

            //var coordenadas = {lat:airport.location.latitude, lng: airport.location.longitude}
            //marcadores.push(coordenadas);
                var string = String(json[country]);
                var quitar = string.replace("[","");
                quitar = quitar.replace("]","");        
                var final = String(quitar);

                var datosCountry = JSON.parse(final);
                alert(datosCountry.deaths + " " + datosCountry.infected + " " + datosCountry.cured);
                var infected = String(datosCountry.infected);
                console.log(infected);
                //var cured = JSON.parse(final3);
                //alert("DATOS COUNTRY" + datosCountry);
            
                tabla += "<tr>" +
                        "<td>" + country + "</td>" +
                        "<td>" + datosCountry.deaths; + "</td>" +
                        "<td>" + infected + "</td>" +
                        "<td>" + datosCountry.cured + "</td>" +
                    "</tr>";
            }
        }
        
        tabla += "</tbody> </table> </center>";
        return tabla;
    }


    return {

        
        getStatsByCountry: function(name) {
            
            apiClient.getStatsByCountry(name, tableAndMap);

        },
        
        getAll: function() {
            apiClient.getAll(tableAndMap);
        }

    }

})();