import React, { StrictMode } from "react";
import { Provider } from "react-redux";
import store from "./js/store/index";
import TripsContainer from "./js/components/trips/TripsContainer";
import BlogContainer from "./js/components/blog/BlogContainer";
import ReactDOM from "react-dom";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import css from "./index.css";
import { LoginComponent } from "./js/components/core/auth/LoginComponent";
import { SignupComponent } from "./js/components/core/auth/SignupComponent";
import { CurrentUserComponent } from "./js/components/core/auth/CurrentUserComponent";
import { getPosts } from "./js/actions/BlogActions";

store.dispatch(getPosts())

ReactDOM.render(
    <Provider store={store}>
        <Router>
            <div className={css.globalContainer}>
                <ul className={css.sidenav}>
                    <CurrentUserComponent />
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
                <Route path="/sign-in" component={LoginComponent} />
                <Route path="/sign-up" component={SignupComponent} />
            </div>
        </Router>
    </Provider>,
    document.getElementById("app"));
