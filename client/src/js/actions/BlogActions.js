import {GET_POSTS} from "../constants/action-types";


export function getPosts() {
    return (dispatch) => {
        return fetch("http://localhost:8080/blog/all", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
            credentials: "include",
            mode: "cors"
        }).then(response => {
            if (response.ok) {
                response.json().then(json => {
                    console.log(json);
                    dispatch({
                        type: GET_POSTS,
                        payload: json
                    });
                })
            }
        });
    }
}