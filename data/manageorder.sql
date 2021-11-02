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

--
-- Name: Order; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Order" (
    id integer NOT NULL,
    "orderNumber" character varying(20) NOT NULL,
    "orderDate" date NOT NULL,
    "orderDescription" character varying(50),
    "totalPrice" double precision
);


ALTER TABLE public."Order" OWNER TO postgres;

--
-- Name: OrderDetail_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."OrderDetail_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."OrderDetail_id_seq" OWNER TO postgres;

--
-- Name: OrderDetail_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."OrderDetail_id_seq" OWNED BY public."Order".id;


--
-- Name: Shape; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Shape" (
    id integer NOT NULL,
    "orderId" integer NOT NULL,
    property json NOT NULL,
    name character varying(20) NOT NULL,
    material character varying(20) NOT NULL
);


ALTER TABLE public."Shape" OWNER TO postgres;

--
-- Name: Shape_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Shape_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Shape_id_seq" OWNER TO postgres;

--
-- Name: Shape_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Shape_id_seq" OWNED BY public."Shape".id;


--
-- Name: Order id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Order" ALTER COLUMN id SET DEFAULT nextval('public."OrderDetail_id_seq"'::regclass);


--
-- Name: Shape id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Shape" ALTER COLUMN id SET DEFAULT nextval('public."Shape_id_seq"'::regclass);


--
-- Data for Name: Order; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Order" (id, "orderNumber", "orderDate", "orderDescription", "totalPrice") FROM stdin;
26	3232	2021-11-18		2714256
22	3223	2021-11-30		1447228.8599999996
13	3232	2021-10-28		726067
30	32	2021-11-24		NaN
31	3232	2021-11-09		1507824
32	3223	2021-11-25		1447286.43
\.


--
-- Data for Name: Shape; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Shape" (id, "orderId", property, name, material) FROM stdin;
42	32	{"height": 3.00, "radius": 4.00}	Cone	Wooden
37	22	{"height": 3.00, "radius": 4.00}	Cone	Wooden
15	13	{"height": 5.00, "radius": 2.00}	Cone	Plastic
3	13	{"height": 5.00, "radius": 2.00}	Cone	Plastic
39	13	{"height": 3.00, "radius": 4.00}	Cone	Wooden
\.


--
-- Name: OrderDetail_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."OrderDetail_id_seq"', 32, true);


--
-- Name: Shape_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Shape_id_seq"', 42, true);


--
-- Name: Order OrderDetail_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Order"
    ADD CONSTRAINT "OrderDetail_pkey" PRIMARY KEY (id);


--
-- Name: Shape Shape_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Shape"
    ADD CONSTRAINT "Shape_pkey" PRIMARY KEY (id);


--
-- Name: Shape fk_shapeid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Shape"
    ADD CONSTRAINT fk_shapeid FOREIGN KEY ("orderId") REFERENCES public."Order"(id) NOT VALID;


--
-- PostgreSQL database dump complete
--

