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
    
    var tableAndMap = function(error, json, name) {
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
        json.forEach(function(country) {
            //var coordenadas = {lat:airport.location.latitude, lng: airport.location.longitude}
            //marcadores.push(coordenadas);
            tabla += "<tr>" +
                        "<td>" + name + "</td>" +
                        "<td>" + country.deaths + "</td>" +
                        "<td>" + country.infected + "</td>" +
                        "<td>" + country.cured + "</td>" +
                    "</tr>";

        });
        tabla += "</tbody> </table> </center>";
        return tabla;
    }


    return {

        
        getStatsByCountry: function(name) {
            console.log(name);
            apiClient.getStatsByCountry(name, tableAndMap);

        }

    }

})();