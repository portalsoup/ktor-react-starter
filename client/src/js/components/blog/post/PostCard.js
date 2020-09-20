import React, { Component } from "react";
import css from "./PostCard.css";
import RouteMap from "../../trips/map/RouteMap";
import * as L from "leaflet";
import moment from "moment";

export default class PostCard extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className={css.postFrame}>
                {
                    this.props.post.route &&
                    <RouteMap
                        polyline={L.polyline(
                            this.props.post.route.coordinates.map(coord => L.latLng(coord.lat, coord.lng, coord.altitude)),
                            {color:'red'}
                        )}
                        bounds={1}
                        width="100%"
                        height="300px"
                    />
                }
                <h2>{this.props.post.title}</h2>
                <div>{moment(this.props.post.timePosted).format("MM-DD-YYYY")}</div>
                <p>{this.props.post.body}</p>
            </div>
        )
    }

}