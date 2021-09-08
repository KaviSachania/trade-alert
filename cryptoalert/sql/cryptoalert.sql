--
-- PostgreSQL database dump
--

-- Dumped from database version 13.3
-- Dumped by pg_dump version 13.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

drop table if exists public.alert cascade;
drop table if exists public.price cascade;
drop table if exists public.stock cascade;

--
-- Name: alert; Type: TABLE; Schema: public; Owner: cryptoalert
--

CREATE TABLE public.alert (
    id character varying(255) NOT NULL,
    direction boolean NOT NULL,
    last_trigger_time bigint DEFAULT 0 NOT NULL,
    lookback_window bigint NOT NULL,
    sleep_time bigint NOT NULL,
    stock_id character varying(255) NOT NULL,
    threshold double precision NOT NULL
);


ALTER TABLE public.alert OWNER TO cryptoalert;

--
-- Name: price; Type: TABLE; Schema: public; Owner: cryptoalert
--

CREATE TABLE public.price (
    id character varying(255) NOT NULL,
    amount double precision NOT NULL,
    stock_id character varying(255) NOT NULL,
    "time" bigint DEFAULT 0 NOT NULL
);


ALTER TABLE public.price OWNER TO cryptoalert;

--
-- Name: stock; Type: TABLE; Schema: public; Owner: cryptoalert
--

CREATE TABLE public.stock (
    id character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    ticker character varying(255) NOT NULL
);


ALTER TABLE public.stock OWNER TO cryptoalert;

--
-- Data for Name: alert; Type: TABLE DATA; Schema: public; Owner: cryptoalert
--

COPY public.alert (id, direction, last_trigger_time, lookback_window, sleep_time, stock_id, threshold) FROM stdin;
asdf	t	1	30	90	btc	0.0005
1423	f	1	30	90	btc	-0.0005
\.


--
-- Data for Name: price; Type: TABLE DATA; Schema: public; Owner: cryptoalert
--

COPY public.price (id, amount, stock_id, "time") FROM stdin;
2f48e6dc-8ada-4ba3-96fe-b3f798b38ad6	1.0662	algo	1629941463
28ae6151-f86d-41f4-9eac-91f4d7f0525d	49036.76	btc	1629941463
f010e800-9c71-46a8-95ca-d5b3412aace0	0.29258	doge	1629941464
0473a9b8-70c1-4d0a-a3e9-26ac06cec181	3227.25	eth	1629941464
\.


--
-- Data for Name: stock; Type: TABLE DATA; Schema: public; Owner: kavisachania
--

COPY public.stock (id, name, ticker) FROM stdin;
btc	Bitcoin	BTC
eth	Ethereum	ETH
doge	DogeCoin	DOGE
algo	Algorand	ALGO
\.


--
-- Name: alert alert_pkey; Type: CONSTRAINT; Schema: public; Owner: kavisachania
--

ALTER TABLE ONLY public.alert
    ADD CONSTRAINT alert_pkey PRIMARY KEY (id);


--
-- Name: price price_pkey; Type: CONSTRAINT; Schema: public; Owner: kavisachania
--

ALTER TABLE ONLY public.price
    ADD CONSTRAINT price_pkey PRIMARY KEY (id);


--
-- Name: stock stock_pkey; Type: CONSTRAINT; Schema: public; Owner: kavisachania
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT stock_pkey PRIMARY KEY (id);


--
-- Name: stock uk_9fjf4aj1d5f2m6wi2uqmdan0a; Type: CONSTRAINT; Schema: public; Owner: kavisachania
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT uk_9fjf4aj1d5f2m6wi2uqmdan0a UNIQUE (name);


--
-- Name: stock uk_t18garrgaqnchmrc3xls507el; Type: CONSTRAINT; Schema: public; Owner: kavisachania
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT uk_t18garrgaqnchmrc3xls507el UNIQUE (ticker);


--
-- PostgreSQL database dump complete
--

