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
drop table if exists public.app_user cascade;
drop table if exists public.candidate cascade;
drop table if exists public.confirmation_token cascade;
drop table if exists public.price cascade;
drop table if exists public.stock cascade;
drop sequence if exists public.confirmation_token_sequence;
drop sequence if exists public.user_sequence;


--
-- Name: alert; Type: TABLE; Schema: public; Owner: kavisachania
--

CREATE TABLE public.alert (
    id character varying(255) NOT NULL,
    min_length integer,
    min_market_cap integer,
    min_slope double precision,
    trend integer NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE public.alert OWNER TO cryptoalert;

--
-- Name: app_user; Type: TABLE; Schema: public; Owner: kavisachania
--

CREATE TABLE public.app_user (
    id bigint NOT NULL,
    email character varying(255),
    enabled boolean,
    locked boolean,
    password character varying(255),
    user_role character varying(255)
);


ALTER TABLE public.app_user OWNER TO cryptoalert;

--
-- Name: candidate; Type: TABLE; Schema: public; Owner: kavisachania
--

CREATE TABLE public.candidate (
    id character varying(255) NOT NULL,
    anchor_price double precision NOT NULL,
    anchor_time integer NOT NULL,
    created_at timestamp without time zone NOT NULL,
    is_new boolean NOT NULL,
    length bigint DEFAULT 0 NOT NULL,
    rebounds integer NOT NULL,
    slope double precision NOT NULL,
    stock_id character varying(255) NOT NULL,
    trend integer NOT NULL
);


ALTER TABLE public.candidate OWNER TO cryptoalert;

--
-- Name: confirmation_token; Type: TABLE; Schema: public; Owner: kavisachania
--

CREATE TABLE public.confirmation_token (
    id bigint NOT NULL,
    confirmed_at timestamp without time zone,
    created_at timestamp without time zone NOT NULL,
    expires_at timestamp without time zone NOT NULL,
    token character varying(255) NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE public.confirmation_token OWNER TO cryptoalert;

--
-- Name: confirmation_token_sequence; Type: SEQUENCE; Schema: public; Owner: kavisachania
--

CREATE SEQUENCE public.confirmation_token_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.confirmation_token_sequence OWNER TO cryptoalert;

--
-- Name: price; Type: TABLE; Schema: public; Owner: kavisachania
--

CREATE TABLE public.price (
    id character varying(255) NOT NULL,
    close double precision NOT NULL,
    high double precision NOT NULL,
    low double precision NOT NULL,
    open double precision NOT NULL,
    stock_id character varying(255) NOT NULL,
    "time" bigint DEFAULT 0 NOT NULL
);


ALTER TABLE public.price OWNER TO cryptoalert;

--
-- Name: stock; Type: TABLE; Schema: public; Owner: kavisachania
--

CREATE TABLE public.stock (
    id character varying(255) NOT NULL,
    market_cap integer DEFAULT 0 NOT NULL,
    name character varying(255) NOT NULL,
    ticker character varying(255) NOT NULL
);


ALTER TABLE public.stock OWNER TO cryptoalert;

--
-- Name: user_sequence; Type: SEQUENCE; Schema: public; Owner: kavisachania
--

CREATE SEQUENCE public.user_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_sequence OWNER TO cryptoalert;

--
-- Data for Name: alert; Type: TABLE DATA; Schema: public; Owner: kavisachania
--

COPY public.alert (id, min_length, min_market_cap, min_slope, trend, user_id) FROM stdin;
\.


--
-- Data for Name: app_user; Type: TABLE DATA; Schema: public; Owner: kavisachania
--

COPY public.app_user (id, email, enabled, locked, password, user_role) FROM stdin;
\.


--
-- Data for Name: confirmation_token; Type: TABLE DATA; Schema: public; Owner: kavisachania
--

COPY public.confirmation_token (id, confirmed_at, created_at, expires_at, token, user_id) FROM stdin;
\.


--
-- Data for Name: price; Type: TABLE DATA; Schema: public; Owner: kavisachania
--

COPY public.price (id, close, high, low, open, stock_id, "time") FROM stdin;
\.


--
-- Data for Name: stock; Type: TABLE DATA; Schema: public; Owner: kavisachania
--

COPY public.stock (id, market_cap, name, ticker) FROM stdin;
cb22bf1e-b1ec-4f1b-9c68-6c854a9d9140	142	MOTION ACQUISITION CORP-CL A	MOTN
dc1fa753-ed1d-4880-a5f3-b9a21c561ba6	663	COMTECH TELECOMMUNICATIONS	CMTL
cafc11bc-6dd9-4fda-a102-d7ed91cfa5c8	515	DIGIMARC CORP	DMRC
0d00d2de-3323-478e-a4e3-ec7db91fa6ba	91	OCEAN BIO-CHEM INC	OBCI
52c7f9ea-e64c-4fe5-a453-f61a4ff4123b	44	HUDSON GLOBAL INC	HSON
a048bd2c-9a58-4d1f-8fb0-102095cd77d5	118	SIEBERT FINANCIAL CORP	SIEB
e096497f-a2d3-40d8-b958-1e0130367ad8	1196	HOLLYSYS AUTOMATION TECHNOLO	HOLI
2ffdfe58-858d-494a-9455-481255f7efde	425	INMUNE BIO INC	INMB
0d5b914d-7b8c-44ef-99d3-1e325a7698c6	506	EQUITY DISTRIBUTION ACQUIS-A	EQD
ac14316a-5420-4b47-abe0-ca6fd65056c3	754	TCG BDC INC	CGBD
1f2a712e-4192-474a-806c-5216bc89cd77	449	RAYONIER ADVANCED MATERIALS	RYAM
a8d33a03-48ea-4920-9d54-c39049931df5	3679	ARENA PHARMACEUTICALS INC	ARNA
dca1f1d1-23cc-4a14-8b13-7b2625e35501	46550	BANK OF NEW YORK MELLON CORP	BK
c23b1d66-5391-4b29-9686-58859d3f83cd	3336	NORTHWESTERN CORP	NWE
fec4778d-f40d-46ee-930d-f817e437f79e	3875	ROGERS CORP	ROG
15466aca-c982-4082-a203-e99d3a4f3e86	2720	LIONS GATE ENTERTAINMENT-B	LGF.B
b5e05345-fd63-4dce-b14d-caaf6f589006	510	MONTES ARCHIMEDES ACQUISIT-A	MAAC
9d2615a2-cb9e-4231-9f92-c37963b68491	5819	RISKIFIED LTD-A	RSKD
6fb8f450-b215-4903-bf4b-feeb0fc04567	262617	PFIZER INC	PFE
a9849e3b-fecc-49c4-8ff4-3d843f604d84	266	SANARA MEDTECH INC	SMTI
b135dd87-b4de-4fa3-825f-9efe95f165c1	1302	MARTEN TRANSPORT LTD	MRTN
70ad6f20-0685-4966-aae1-8e8979818f73	212	HORIZON GLOBAL CORP	HZN
\.


--
-- Data for Name: candidate; Type: TABLE DATA; Schema: public; Owner: kavisachania
--

COPY public.candidate (id, anchor_price, anchor_time, created_at, is_new, length, rebounds, slope, stock_id, trend) FROM stdin;
a656fe34-3377-483a-b7fc-ba66a9b2ad7f	9.61	115	2021-08-05 13:18:06.629467	f	117	9	0.14118975903614528	b5e05345-fd63-4dce-b14d-caaf6f589006	0
4c8ba56b-ff7b-4c5c-a026-81cd76d54501	16.48	116	2021-09-05 13:18:06.535232	t	116	3	0.0638267355134828	52c7f9ea-e64c-4fe5-a453-f61a4ff4123b	0
74ebe439-8fcd-4c8d-9b54-32d18f5c034e	9.7	113	2021-09-05 13:18:06.449038	t	193	9	-0.045397798206787014	0d5b914d-7b8c-44ef-99d3-1e325a7698c6	0
9ce69ebe-1903-48fc-a14d-7b35fad42d2c	9.77	112	2021-09-05 13:18:06.648671	t	126	11	0.06241742694560373	cb22bf1e-b1ec-4f1b-9c68-6c854a9d9140	0
\.


--
-- Name: confirmation_token_sequence; Type: SEQUENCE SET; Schema: public; Owner: kavisachania
--

SELECT pg_catalog.setval('public.confirmation_token_sequence', 1, false);


--
-- Name: user_sequence; Type: SEQUENCE SET; Schema: public; Owner: kavisachania
--

SELECT pg_catalog.setval('public.user_sequence', 1, false);


--
-- Name: alert alert_pkey; Type: CONSTRAINT; Schema: public; Owner: kavisachania
--

ALTER TABLE ONLY public.alert
    ADD CONSTRAINT alert_pkey PRIMARY KEY (id);


--
-- Name: app_user app_user_pkey; Type: CONSTRAINT; Schema: public; Owner: kavisachania
--

ALTER TABLE ONLY public.app_user
    ADD CONSTRAINT app_user_pkey PRIMARY KEY (id);


--
-- Name: candidate candidate_pkey; Type: CONSTRAINT; Schema: public; Owner: kavisachania
--

ALTER TABLE ONLY public.candidate
    ADD CONSTRAINT candidate_pkey PRIMARY KEY (id);


--
-- Name: confirmation_token confirmation_token_pkey; Type: CONSTRAINT; Schema: public; Owner: kavisachania
--

ALTER TABLE ONLY public.confirmation_token
    ADD CONSTRAINT confirmation_token_pkey PRIMARY KEY (id);


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
-- Name: confirmation_token fke0flsojr3yhckm24wh4e4xyj4; Type: FK CONSTRAINT; Schema: public; Owner: kavisachania
--

ALTER TABLE ONLY public.confirmation_token
    ADD CONSTRAINT fke0flsojr3yhckm24wh4e4xyj4 FOREIGN KEY (user_id) REFERENCES public.app_user(id);


--
-- Name: alert fko4g1hp581v68de3k59tsyrskq; Type: FK CONSTRAINT; Schema: public; Owner: kavisachania
--

ALTER TABLE ONLY public.alert
    ADD CONSTRAINT fko4g1hp581v68de3k59tsyrskq FOREIGN KEY (user_id) REFERENCES public.app_user(id);


--
-- PostgreSQL database dump complete
--

