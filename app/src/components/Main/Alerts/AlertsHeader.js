import React, { Component } from "react";
import Button from "react-bootstrap/Button";
import AddAlertModal from "./AddAlertModal";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPlus} from "@fortawesome/free-solid-svg-icons";

class AlertsHeader extends Component {

    constructor(props) {
        super(props);
        this.state = {
            showAddAlertModal: false
        };
        this.openAddAlertModal = this.openAddAlertModal.bind(this);
        this.closeAddAlertModal = this.closeAddAlertModal.bind(this);
    }

    openAddAlertModal = (e) => {
        if (this.props.loggedIn) {
            this.setState({showAddAlertModal: true});
        }
    };

    closeAddAlertModal = () => {
        this.setState({showAddAlertModal: false});
    };

// <Button
// variant={"outline-success"}
// value={'add-alert'}
// onClick={this.openAddAlertModal}
// >
// +
// </Button> :
    
    render() {
        return (
            <div id={"alerts-header"}>
                <h3>Alerts</h3>
                {this.props.loggedIn ?
                    <a className={"clickable"}>
                        <FontAwesomeIcon
                            id={"add-alert-button"}
                            icon={faPlus}
                            onClick={this.openAddAlertModal}
                            style={{color: "#198754"}}
                        />
                    </a> :
                    <div>&nbsp;</div>
                }
                <AddAlertModal
                    showAddAlertModal={this.state.showAddAlertModal}
                    openAddAlertModal={this.openAddAlertModal}
                    closeAddAlertModal={this.closeAddAlertModal}
                    loadAlerts={this.props.loadAlerts}
                />
            </div>
        );
    }
}

export default AlertsHeader;
