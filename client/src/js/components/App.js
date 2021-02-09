import React from "react";
import { Provider } from "react-redux";
import store from "../store/index";
import TripsContainer from "./trips/TripsContainer";
import { BrowserRouter as Router, Route } from "react-router-dom";
import css from "./App.css";
import { LoginComponent } from "./core/auth/LoginComponent";
import { SignupComponent } from "./core/auth/SignupComponent";
import { NavComponent } from "./core/NavComponent"
import { PostListComponent } from "./blog/PostListComponent";

export const AppComponent = (props) => {
    
    return (<Provider store={store}>
        <Router>
            <NavComponent></NavComponent>
            <div className={css.globalContainer}>
                <Route exact path="/" component={TripsContainer} />
                <Route path="/blog" component={PostListComponent} />
                <Route path="/sign-in" component={LoginComponent} />
                <Route path="/sign-up" component={SignupComponent} />
            </div>
        </Router>
    </Provider>)
}