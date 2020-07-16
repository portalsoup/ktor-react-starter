import React from "react";
import { Provider } from "react-redux";
import store from "./js/store/index";
import TripsContainer from "./js/components/trips/TripsContainer";
import BlogContainer from "./js/components/blog/BlogContainer";
import LoginForm from "./js/components/core/auth/LoginForm";
import SignupForm from "./js/components/core/auth/SignupForm";
import ReactDOM from "react-dom";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import css from "./index.css";
import CurrentUser from "./js/components/core/CurrentUser";

ReactDOM.render(
    <Provider store={store}>
        <Router>
            <div className={css.globalContainer}>
                <ul className={css.sidenav}>
                    <CurrentUser />
                    <li>
                        <Link to="/">Home</Link>
                    </li>
                    <li>
                        <Link to="/blog">Blog</Link>
                    </li>
                    <li>
                        <Link to="/sign-in">Login</Link>
                    </li>
                    <li>
                        <Link to="/sign-up">Sign up</Link>
                    </li>
                </ul>
                <Route exact path="/" component={TripsContainer} />
                <Route path="/blog" component={BlogContainer} />
                <Route path="/sign-in" component={LoginForm} />
                <Route path="/sign-up" component={SignupForm} />
            </div>
        </Router>
    </Provider>,
    document.getElementById("app"));
