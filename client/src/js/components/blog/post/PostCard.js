import React from "react";
import css from "./PostCard.css";
import RouteMap from "../../trips/map/RouteMap";
import * as L from "leaflet";
import moment from "moment";

export const PostCard = (props) => {
    
    return (
        <div className={css.postFrame}>
            {
                props.post.route &&
                <RouteMap
                    polyline={L.polyline(
                        props.post.route.coordinates.map(coord => L.latLng(coord.lat, coord.lng, coord.altitude)),
                        {color:'red'}
                    )}
                    bounds={1}
                    width="100%"
                    height="300px"
                />
            }
            <h2>{props.post.title}</h2>
            <div>{moment(props.post.timePosted).format("MM-DD-YYYY")}</div>
            <p>{props.post.body}</p>
        </div>
    )
}