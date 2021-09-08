import React, { Component } from "react";
import Modal from 'react-bootstrap/Modal';
import ModalHeader from 'react-bootstrap/ModalHeader';
import ModalTitle from 'react-bootstrap/ModalTitle';
import ModalBody from 'react-bootstrap/ModalBody';
import ModalFooter from 'react-bootstrap/ModalFooter';
import Button from 'react-bootstrap/Button';

import axios from "axios";
import Form from "react-bootstrap/Form";

class DeleteAlertModal extends Component {

    constructor(props) {
        super(props);
        this.deleteAlert = this.deleteAlert.bind(this);
        this.handleCloseDeleteAlertModal = this.handleCloseDeleteAlertModal.bind(this);
    }

    deleteAlert = async () => {
        await axios
            .delete("/alerts/"+this.props.alert.id)
            .then(() => this.props.closeDeleteAlertModal())
            .then(() => this.props.loadAlerts())
            .catch((err) => console.log(err));
    };

    handleCloseDeleteAlertModal = () => {
        this.props.closeDeleteAlertModal();
    };

    render() {
        return (
            <Modal
                animation={false}
                show={this.props.showDeleteAlertModal}
                onHide={this.handleCloseDeleteAlertModal}
            >
                <Modal.Header className={"modal-header-delete"} closeButton={true} closeLabel={''} closeVariant={'white'}>
                    <Modal.Title>Delete Alert</Modal.Title>
                </Modal.Header>
                <div className={'delete-alert-modal'}>
                    <Modal.Body>
                        Are you sure you want to delete this alert?
                    </Modal.Body>
                </div>
                <Modal.Footer className={"modal-footer"}>
                    <Button id={'delete-alert-btn'} variant="danger" onClick={this.deleteAlert}>
                        Delete
                    </Button>
                    <Button variant="secondary" onClick={this.handleCloseDeleteAlertModal}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }
}

export default DeleteAlertModal;
