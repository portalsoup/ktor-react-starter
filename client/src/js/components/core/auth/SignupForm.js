import React, { Component } from "react";
import { connect } from "react-redux";
import { onSignUp } from "../../../actions/AuthActions";

class SignupForm extends Component {
    constructor(props) {
        super(props);

        this.state = {
            email: "",
            password: ""
        };
    }

    handleSignUp(e) {
        e.preventDefault();
        let username = this.refs.username.value;
        let password = this.refs.password.value;
        this.props.onSignUp(username, password)
    }

    render() {
        return (
            <form onSubmit={this.handleSignUp.bind(this)}>
                <h3>Sign Up</h3>
                <input type="text" ref="username" placeholder="enter you username" />
                <input type="password" ref="password" placeholder="enter password" />
                <input type="submit" value="Sign up" />
            </form>
        )
    }

}

function mapDispatchToProps(dispatch) {
    return {
        onSignUp: (username, password) => dispatch(onSignUp(username, password))
    };
}

export default connect(null, mapDispatchToProps)(SignupForm);