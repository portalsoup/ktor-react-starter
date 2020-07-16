import React, { Component } from "react";
//import { connect } from "react-redux";
import { getCurrentUser } from "../../actions/AuthActions";

import connect from "react-redux/es/connect/connect";

class CurrentUser extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <button onClick={() => this.props.onClick()}>Current user</button>
        );
    }

}

function mapDispatchToProps(dispatch) {
    return {
        onClick: () => dispatch(getCurrentUser())
    };
}

export default connect(null, mapDispatchToProps)(CurrentUser);