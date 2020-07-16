import React, { Component } from "react";
import { Map, Marker, Popup, TileLayer, Polyline } from "react-leaflet";
import "leaflet/dist/leaflet.css";


delete L.Icon.Default.prototype._getIconUrl;

L.Icon.Default.mergeOptions({
    iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
    iconUrl: require('leaflet/dist/images/marker-icon.png'),
    shadowUrl: require('leaflet/dist/images/marker-shadow.png')
});

export default class RouteMap extends Component {
    render () {

        const dimensions = {
            width: `${this.props.width}`,
            height: `${this.props.height}`
        };

        return (
            <div>
                {this.props.polyline &&
                <Map style={dimensions} center={this.props.position} zoom={this.props.zoom}
                     bounds={this.props.polyline.getBounds(this.props.bounds)}>
                    <TileLayer // http://{s}.tile.osm.org/{z}/{x}/{y}.png free simple tiles
                        url='https://tile.thunderforest.com/cycle/{z}/{x}/{y}.png?apikey=bf035ff674364bd0b3894f41d665642f' // thunderforest.com
                        attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                    />
                    {this.props.polyline &&
                    <Polyline color={this.props.polyline.options.color} positions={this.props.polyline.getLatLngs()}/>}
                </Map>
                }
            </div>
        )
    }
}
