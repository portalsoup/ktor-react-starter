import React, { Component } from "react";
//import { connect } from "react-redux";
import { getCurrentUser } from "../../actions/AuthActions";

import connect from "react-redux/es/connect/connect";

class CurrentUser extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const user = this.props.currentUser.name || "No user"
        return (
            <label
                style={{color: "white"}}
            >{user}</label>
        );
    }

}

function mapDispatchToProps(dispatch) {
    return {
        onClick: () => dispatch(getCurrentUser())
    };
}

function mapStateToProps(state) {
    return {
        currentUser: state.currentUser
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(CurrentUser);