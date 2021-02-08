// import axios from 'axios'; 
import "regenerator-runtime/runtime";
import { CURRENT_USER, NO_CURRENT_USER } from "../constants/action-types";
import { getPosts } from "./BlogActions";

export function onSignUp(email, password) {
    return async (dispatch) => {
        const response = await fetch(`http://localhost:8080/sign-up`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ email: email, password: password }),
        });
        if (response.ok) {
            console.log("ok?");
        }
    }
}

export function onLogIn(email, password) {
    console.log(`Dispatching login: ${email} ${password}`)
    return (dispatch) => {
        return fetch(`http://localhost:8080/sign-in`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({email: email, password: password}),
            credentials: 'include',
        }).then(response => {
            if (response.ok) {
                console.log("Success");
            }
            dispatch(getCurrentUser())
            dispatch(getPosts())
        })
    }
}

export function onLogOut() {
    return (dispatch) => {
        return fetch(`http://localhost:8080/sign-out`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "include"
        }).then(response => {
            if (response.ok) {
                console.log("Success")
            }

            dispatch(getCurrentUser())
            dispatch(getPosts())
        })
    }
}

export function getCurrentUser() {
    console.log(`getting current user`);
    return (dispatch) => {
        return fetch(`http://localhost:8080/currentUser`, {
            method: "GET",
            headers: {
            },
            credentials: 'include',
        }).then(response => {
            if (response.ok) {
                response.json().then(json => {
                    console.log(`About to set state with ${JSON.stringify(json)}`)
                    dispatch({
                        type: CURRENT_USER,
                        payload: json
                    });
                })
            } else {
                dispatch({
                    type: NO_CURRENT_USER,
                    payload: {
                        id: undefined,
                        name: undefined,
                    }
                })
            }
        })
    }
}
