import React, { Component } from "react";

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash } from '@fortawesome/free-solid-svg-icons';

class AlertRow extends Component {
    constructor(props) {
        super(props);
        this.deleteAlert = this.deleteAlert.bind(this);
    }

    deleteAlert = () => {
        this.props.setAlertForDeletion(this.props.alert);
        this.props.openDeleteAlertModal();
    };

    render() {
        let alert = this.props.alert;

        let marketCapString = "";
        let marketCap = alert.minMarketCap;
        if (marketCap != null) {
            let amountLabel;

            if (marketCap >= 1000000) {
                marketCap = marketCap / 1000000;
                amountLabel = "T";
            } else if (marketCap >= 1000) {
                marketCap = marketCap / 1000;
                amountLabel = "B";
            } else {
                amountLabel = "M";
            }

            marketCapString = marketCap.toPrecision(3) + " " + amountLabel;
        }

        let slopeStyle = "";
        let displaySlope = alert.minSlope;
        if (displaySlope != null) {
            let slope = displaySlope.toFixed(3);

            if (slope > 0) {
                slopeStyle = "#198754";
            } else if (slope < 0) {
                slopeStyle = "#DE0202";
            } else {
                slopeStyle = "#FFFFFF";
            }

            if (slope === "-0.000") {
                displaySlope = 0;
            }
        }

        return (
            <tr className={"alerts-table-content"}>
                <td className={"alerts-table-column"}>
                    {alert.minMarketCap != null ?
                        marketCapString :
                        <span>-</span>
                    }
                </td>
                <td className={"alerts-table-column"} style={{color: slopeStyle}}>
                    {alert.minSlope != null ?
                        displaySlope.toLocaleString()+ "%" :
                        <span>-</span>
                    }
                </td>
                <td className={"alerts-table-column"}>
                    {alert.minLength != null ?
                        alert.minLength.toLocaleString() :
                        <span>-</span>
                    }
                </td>
                <td className={"alerts-table-column"}>
                    <a className={"clickable"}>
                        <FontAwesomeIcon icon={faTrash} onClick={this.deleteAlert} style={{color: "#DE0202"}} />
                    </a>
                </td>
            </tr>
        );
    }
}

export default AlertRow;
