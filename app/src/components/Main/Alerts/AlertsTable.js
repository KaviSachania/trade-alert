import React, { Component } from "react";
import Table from 'react-bootstrap/Table';
import axios from 'axios';

import AlertRow from './AlertRow';
import AlertsHeader from "./AlertsHeader";
import DeleteAlertModal from "./DeleteAlertModal";
import AddAlertModal from "./AddAlertModal";

class AlertsTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showDeleteAlertModal: false,
            alertForDeletion: {}
        };
        this.openDeleteAlertModal = this.openDeleteAlertModal.bind(this);
        this.closeDeleteAlertModal = this.closeDeleteAlertModal.bind(this);
    }

    openDeleteAlertModal = () => {
        if (this.props.loggedIn) {
            this.setState({showDeleteAlertModal: true});
        }
    };

    closeDeleteAlertModal = () => {
        this.setState({showDeleteAlertModal: false});
    };

    setAlertForDeletion = (alert) => {
        this.setState({alertForDeletion: alert});
    };

    componentDidMount() {
        this.props.loadAlerts();
    }

    render() {
        if (!this.props.loggedIn) {
            return (
                <div id={"alerts-table"}>
                    Log in to view email alerts.
                </div>
            );
        }

        let alertRows = [];
        for (let alertI = 0; alertI < this.props.alerts.length; alertI++) {
            alertRows.push(<AlertRow
                alert={this.props.alerts[alertI]}
                setAlertForDeletion={this.setAlertForDeletion}
                openDeleteAlertModal={this.openDeleteAlertModal}
                loadAlerts={this.props.loadAlerts}
            />)
        }

        return (
            <div>
                <Table id={"alerts-table"}>
                    <thead>
                        <tr className={"alerts-table-header-row"}>
                            <th>Market Cap.</th>
                            <th>+/-</th>
                            <th>Days</th>
                            <th>&nbsp;</th>
                        </tr>
                    </thead>
                    <tbody>
                        {alertRows}
                    </tbody>
                </Table>
                <DeleteAlertModal
                    alert={this.state.alertForDeletion}
                    showDeleteAlertModal={this.state.showDeleteAlertModal}
                    closeDeleteAlertModal={this.closeDeleteAlertModal}
                    loadAlerts={this.props.loadAlerts}
                />
            </div>
        );
    }
}

export default AlertsTable;
