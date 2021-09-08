import React, { Component } from "react";
import Modal from 'react-bootstrap/Modal';
import ModalHeader from 'react-bootstrap/ModalHeader';
import ModalTitle from 'react-bootstrap/ModalTitle';
import ModalBody from 'react-bootstrap/ModalBody';
import ModalFooter from 'react-bootstrap/ModalFooter';
import Button from 'react-bootstrap/Button';

import Register from "./Register";
import Login from "./Login";
import axios from "axios";

class AuthModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            displayEmailMessage: false,
            invalidEmail: false,
            usernameExists: false,
            passwordMissing: false,
            loginFailed: false
        };
        this.handleOpenAuthModal = this.handleOpenAuthModal.bind(this);
        this.handleCloseAuthModal = this.handleCloseAuthModal.bind(this);
        this.handleRegisterSubmit = this.handleRegisterSubmit.bind(this);
        this.handleLoginSubmit = this.handleLoginSubmit.bind(this);
    }

    emailRegex = new RegExp("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$", "i");

    handleOpenAuthModal = (e) => {
        this.setState({
            displayEmailMessage: false,
            invalidEmail: false,
            usernameExists: false,
            passwordMissing: false,
            loginFailed: false
        });
        this.props.openAuthModal(e);
    };

    handleCloseAuthModal = () => {
        this.setState({
            displayEmailMessage: false,
            invalidEmail: false,
            usernameExists: false,
            passwordMissing: false,
            loginFailed: false
        });
        this.props.closeAuthModal();
    };

    handleRegistrationError = (err) => {
        this.setState({
            usernameExists: (err.response.data.message==="email already taken")
        });
    };

    handleRegisterSubmit = () => {
        const email = document.getElementById("registration-username-input").value;
        const password = document.getElementById("registration-password-input").value;

        let disabled = false;

        if (!this.emailRegex.test(email)) {
            this.setState({invalidEmail: true});
            disabled = true;
        } else {
            this.setState({invalidEmail: false});
        }

        if (password.length === 0) {
            this.setState({passwordMissing: true});
            disabled = true;
        } else {
            this.setState({passwordMissing: false});
        }

        if (disabled) {
            return null;
        }

        console.log(email);
        const data = {
            email: email,
            password: password,
        };

        axios
            .post("/registration", data)
            .then(() => this.setState({
                displayEmailMessage: true,
                invalidEmail: false,
                usernameExists: false,
                passwordMissing: false
            }))
            .catch((err) => this.handleRegistrationError(err));
    };

    handleLoginError = (err) => {
        // if (err.response.data.error==="Not Found") {
        //     this.setState({loginFailed: true});
        // }
        this.setState({loginFailed: true});
    };

    handleLoginSubmit = () => {
        const username = document.getElementById("login-username-input").value;
        const password = document.getElementById("login-password-input").value;

        let bodyFormData = new FormData();
        bodyFormData.append('username', username);
        bodyFormData.append('password', password);

        axios
            .post("/login", bodyFormData)
            .then(() => this.props.closeAuthModal())
            .then(() => this.props.setUser())
            .then(() => this.setState({loginFailed: false}))
            .catch((err) => this.handleLoginError(err));
    };

    render() {
        let submitButton = null;
        if (this.props.isRegistrationModal) {
            if (!this.state.displayEmailMessage) {
                submitButton = (
                    <Button id={'submit-registration-btn'} variant="success" onClick={this.handleRegisterSubmit}>
                        Submit
                    </Button>
                );
            }
        } else {
            submitButton = (
                <Button id={'submit-login-btn'} variant="success" onClick={this.handleLoginSubmit}>
                    Submit
                </Button>
            );
        }

        return (
            <Modal
                animation={false}
                show={this.props.showAuthModal}
                onHide={this.handleCloseAuthModal}
            >
                <Modal.Header className={"modal-header"} closeButton={true} closeLabel={''} closeVariant={'white'}>
                    <Modal.Title>{this.props.isRegistrationModal ? 'Sign Up' : 'Log In'}</Modal.Title>
                </Modal.Header>
                <div className={'auth-modal'}>
                    {this.props.isRegistrationModal ?
                        <Register
                            handleOpenAuthModal={this.handleOpenAuthModal}
                            invalidEmail={this.state.invalidEmail}
                            usernameExists={this.state.usernameExists}
                            displayEmailMessage={this.state.displayEmailMessage}
                            passwordMissing={this.state.passwordMissing}
                        /> :
                        <Login
                            handleOpenAuthModal={this.handleOpenAuthModal}
                            loginFailed={this.state.loginFailed}
                        />
                    }
                </div>
                <Modal.Footer className={"modal-footer"}>
                    {submitButton}
                    <Button variant="secondary" onClick={this.handleCloseAuthModal}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }
}

export default AuthModal;
