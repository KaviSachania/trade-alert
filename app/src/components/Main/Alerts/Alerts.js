import React, { Component } from "react";
import AlertsHeader from "../Alerts/AlertsHeader";
import AlertsTable from "../Alerts/AlertsTable";
import axios from "axios";

class Alerts extends Component {
    constructor(props) {
        super(props);
        this.state = {
            alerts: [],
        };
    }

    loadAlerts = async () => {
        await axios
            .get("/alerts/")
            .then((res) => this.setState({alerts: res.data.results}))
            .then(() => console.log(this.state.alerts))
            .catch((err) => console.log(err));
    };

    render() {
        return (
            <div id={"alerts"}>
                <AlertsHeader
                    loadAlerts={this.loadAlerts}
                    loggedIn={this.props.loggedIn}
                />
                <AlertsTable
                    alerts={this.state.alerts}
                    loggedIn={this.props.loggedIn}
                    loadAlerts={this.loadAlerts}
                />
            </div>
        );
    }
}

export default Alerts;
