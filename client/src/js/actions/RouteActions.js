import { GET_ROUTE } from "../constants/action-types";

export function getRoute(id) {
    return (dispatch) => {
        return fetch("http://localhost:8080/api/v1/route/${id}", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
            credentials: "include",
            mode: "cors"
        }).then(response => {
            if (response.ok) {
                response.json().then(json => {
                    dispatch({
                        type: GET_ROUTE,
                        payload: json.coordinates
                    });
                })
            }
        });
    }
}