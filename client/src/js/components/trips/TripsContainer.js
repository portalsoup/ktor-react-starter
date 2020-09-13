import React, { Component } from "react";
import { connect } from "react-redux";
import RouteMap from "./map/RouteMap";
import { getRoute, setRoute } from "../../actions/RouteActions";
import { uploadGpxFile } from "../../actions/GpxActions";

import css from './TripsContainer.css';
import * as L from "leaflet";

class TripsContainer extends Component {
    constructor(props) {
        super(props);
        this.state = {
            seo_title: ""
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleUpload = this.handleUpload.bind(this);
    }
    handleChange(event) {
        this.setState({route: event.target.value});
    }

    handleSubmit(event) {
        event.preventDefault();
        this.props.getRoute(this.state.route);
    }

    handleUpload(event) {
        event.preventDefault();
        this.props.uploadGpxFile(event.target.files[0]);
    }


    render() {
        return (
            <div>
                <div className={css.main}>
                    <input type="file" onChange={this.handleUpload} />
                    <form onSubmit={this.handleSubmit}>
                        Route id: <input type="text" onChange={this.handleChange} /><br/>
                        <input type="submit" value="Submit" />
                    </form>
                    {
                        this.props.route.getLatLngs().length > 1 &&
                        <RouteMap
                            polyline={this.props.route}
                            bounds={5}
                            height={"400px"}
                            width={"600px"}
                        />
                    }
                </div>
                <div>{`${this.props.route.getLatLngs().toString()}`}</div>
            </div>
        );
    }
}

function mapDispatchToProps(dispatch) {
    return {
        getRoute: id => dispatch(getRoute(id)),
        uploadGpxFile: file => dispatch(uploadGpxFile(file))
    };
}

function mapStateToProps(state) {
    const polyline = L.polyline(
        state.currentRoute.map(coord => L.latLng(coord.lat, coord.lng, coord.altitude)),
        {color:'red'}
    );

    return {
        route: polyline
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(TripsContainer);