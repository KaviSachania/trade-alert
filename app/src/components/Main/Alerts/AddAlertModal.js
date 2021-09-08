import React, { Component } from "react";
import Modal from 'react-bootstrap/Modal';
import ModalHeader from 'react-bootstrap/ModalHeader';
import ModalTitle from 'react-bootstrap/ModalTitle';
import ModalBody from 'react-bootstrap/ModalBody';
import ModalFooter from 'react-bootstrap/ModalFooter';
import Button from 'react-bootstrap/Button';

import axios from "axios";
import Form from "react-bootstrap/Form";

class AddAlertModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isMarketCapValid: true,
            isSlopeValid: true,
            isLengthValid: true
        };
        this.handleOpenAddAlertModal = this.handleOpenAddAlertModal.bind(this);
        this.handleCloseAddAlertModal = this.handleCloseAddAlertModal.bind(this);
        this.addAlert = this.addAlert.bind(this);
    }

    handleOpenAddAlertModal = (e) => {
        this.setState({
            isMarketCapValid: true,
            isSlopeValid: true,
            isLengthValid: true
        });
        this.props.openAddAlertModal(e);
    };

    handleCloseAddAlertModal = () => {
        this.setState({
            isMarketCapValid: true,
            isSlopeValid: true,
            isLengthValid: true
        });
        this.props.closeAddAlertModal();
    };

    addAlert = () => {
        const marketCap = document.getElementById("add-market-cap-input").value;
        const slope = document.getElementById("add-slope-input").value;
        const length = document.getElementById("add-length-input").value;

        let disabled = false;
        if ((isNaN(marketCap)) || (marketCap.includes(".")) || (marketCap.includes("-"))) {
            this.setState({isMarketCapValid: false});
            disabled = true;
        } else {
            this.setState({isMarketCapValid: true});
        }

        if (isNaN(slope)) {
            this.setState({isSlopeValid: false});
            disabled = true;
        } else {
            this.setState({isSlopeValid: true});
        }

        if ((isNaN(length)) || (length.includes(".")) || (length.includes("-"))) {
            this.setState({isLengthValid: false});
            disabled = true;
        } else {
            this.setState({isLengthValid: true});
        }

        if (disabled) {
            return null;
        }

        let body = {
            support: 0,
            minSlope: slope,
            minLength: length,
            minMarketCap: marketCap,
        };

        axios
            .post("/alerts", body)
            .then(() => this.props.closeAddAlertModal())
            .then(() => this.props.loadAlerts())
            .catch((err) => console.log(err));

        return null;
    };

    render() {
        return (
            <Modal
                animation={false}
                show={this.props.showAddAlertModal}
                onHide={this.handleCloseAddAlertModal}
            >
                <Modal.Header className={"modal-header"} closeButton={true} closeLabel={''} closeVariant={'white'}>
                    <Modal.Title>Add Alert</Modal.Title>
                </Modal.Header>
                <div className={'alert-modal'}>
                    <Modal.Body className={"alert-modal-body"}>
                        <h3 className={"auth-modal-body-title"}>Create a new alert</h3>
                        <Form className={"alert-form"} >
                            <Form.Group className="alert-form-group mb-7 col-8" controlId="formMarketCap">
                                <Form.Label for={"market-cap"} >Minimum Market Cap ($ Million):</Form.Label>
                                <Form.Control
                                    id={"add-market-cap-input"}
                                    name={"market-cap"}
                                    className={"alert-form-input"}
                                    size="sm"
                                    type="text"
                                    isInvalid={ !this.state.isMarketCapValid }
                                />
                                <Form.Control.Feedback type="invalid">
                                    Must be a positive integer or blank.
                                </Form.Control.Feedback>
                            </Form.Group>

                            <Form.Group className={"alert-form-group mb-7 col-8"} controlId="formSlope">
                                <Form.Label>Minimum Weekly Growth (%):</Form.Label>
                                <Form.Control
                                    id={"add-slope-input"}
                                    className={"alert-form-input"}
                                    size="sm"
                                    type="text"
                                    isInvalid={ !this.state.isSlopeValid }
                                />
                                <Form.Control.Feedback type="invalid">
                                    Must be a number or blank.
                                </Form.Control.Feedback>
                            </Form.Group>

                            <Form.Group className={"alert-form-group mb-7 col-8"} controlId="formLength">
                                <Form.Label>Minimum Time Range (days):</Form.Label>
                                <Form.Control
                                    id={"add-length-input"}
                                    className={"alert-form-input"}
                                    size="sm"
                                    type="text"
                                    isInvalid={ !this.state.isLengthValid }
                                />
                                <Form.Control.Feedback type="invalid">
                                    Must be a positive integer or blank.
                                </Form.Control.Feedback>
                            </Form.Group>
                        </Form>
                    </Modal.Body>
                </div>
                <Modal.Footer className={"modal-footer"}>
                    <Button
                        id={'add-alert-btn'}
                        variant="success"
                        onClick={this.addAlert}
                    >
                        Submit
                    </Button>
                    <Button variant="secondary" onClick={this.handleCloseAddAlertModal}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }
}

export default AddAlertModal;
