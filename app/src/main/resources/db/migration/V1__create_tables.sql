create schema if not exists warehouse;

create table warehouse.products (
    id serial not null,
    name text not null,
    price numeric not null constraint positive_prive check (price >= 0),
    qty integer not null constraint positive_qty check (qty >= 0),
    constraint products_pk primary key (id)
);