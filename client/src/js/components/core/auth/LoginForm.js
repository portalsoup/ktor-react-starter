import React, { Component } from "react";
//import { connect } from "react-redux";

import css from "./LoginForm.css";
import { onLogIn } from "../../../actions/AuthActions";
import connect from "react-redux/es/connect/connect";

class LoginForm extends Component {
    constructor(props) {
        super(props);

        this.state = {
            email: "",
            password: ""
        };

    }

    handleSignIn(e) {
        e.preventDefault();
        let username = this.refs.username.value;
        let password = this.refs.password.value;
        this.props.onLogIn(username, password)
    }

    render() {
        return (
            <form onSubmit={this.handleSignIn.bind(this)}>
                <h3>Sign in</h3>
                <input type="text" ref="username" placeholder="enter you username" />
                <input type="password" ref="password" placeholder="enter password" />
                <input type="submit" value="Login" />
            </form>
        )
    }

}

function mapDispatchToProps(dispatch) {
    return {
        onLogIn: (username, password) => dispatch(onLogIn(username, password))
    };
}

export default connect(null, mapDispatchToProps)(LoginForm);