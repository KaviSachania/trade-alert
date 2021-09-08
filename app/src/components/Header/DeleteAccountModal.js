import React, { Component } from "react";
import Modal from 'react-bootstrap/Modal';
import ModalHeader from 'react-bootstrap/ModalHeader';
import ModalTitle from 'react-bootstrap/ModalTitle';
import ModalBody from 'react-bootstrap/ModalBody';
import ModalFooter from 'react-bootstrap/ModalFooter';
import Button from 'react-bootstrap/Button';

import axios from "axios";
import Form from "react-bootstrap/Form";

class DeleteAccountModal extends Component {

    constructor(props) {
        super(props);
        this.deleteAccount = this.deleteAccount.bind(this);
        this.handleCloseDeleteAccountModal = this.handleCloseDeleteAccountModal.bind(this);
    }

    deleteAccount = async () => {
        await axios
            .delete("/registration/")
            .then(() => this.props.closeDeleteAccountModal())
            .then(() => this.props.clearUser())
            .catch((err) => console.log(err));
    };

    handleCloseDeleteAccountModal = () => {
        this.props.closeDeleteAccountModal();
    };

    render() {
        return (
            <Modal
                animation={false}
                show={this.props.showDeleteAccountModal}
                onHide={this.handleCloseDeleteAccountModal}
            >
                <Modal.Header className={"modal-header-delete"} closeButton={true} closeLabel={''} closeVariant={'white'}>
                    <Modal.Title>Delete Account</Modal.Title>
                </Modal.Header>
                <div className={'delete-modal'}>
                    <Modal.Body>
                        Are you sure you want to delete this account?
                    </Modal.Body>
                </div>
                <Modal.Footer className={"modal-footer"}>
                    <Button id={'delete-account-btn'} variant="danger" onClick={this.deleteAccount}>
                        Delete
                    </Button>
                    <Button variant="secondary" onClick={this.handleCloseDeleteAccountModal}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }
}

export default DeleteAccountModal;
