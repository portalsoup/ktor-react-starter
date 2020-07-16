import * as L from "leaflet";

function getPolyline(coordinates) {
    return L.polyline(
        coordinates.map(coord => L.latLng(coord.lat, coord.lng, coord.altitude)),
        {color:'red'}
    );
}