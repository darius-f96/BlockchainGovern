create table "address" ("address_id" varchar(255) not null, "apt_number" int4, "building" varchar(255), "city_id" varchar(255), "county_id" varchar(255), "floor" int4, "number" varchar(255), "post_code" varchar(255), "street" varchar(255), "city_city_id" varchar(255), primary key ("address_id"));
create table "app_user" ("app_user_id" varchar(255) not null, "email" varchar(255), "password" varchar(255), "username" varchar(255), primary key ("app_user_id"));
create table "city" ("city_id" varchar(255) not null, "country_id" varchar(255), "county_id" varchar(255), "name" varchar(255), "country_country_id" varchar(255), "county_county_id" varchar(255), primary key ("city_id"));
create table "company" ("company_id" varchar(255) not null, "cui" varchar(255), "description" varchar(255), "name" varchar(255), "reg_identifier" varchar(255), primary key ("company_id"));
create table "company_address" ("company_address_id" varchar(255) not null, "address_id" varchar(255), "company_id" varchar(255), "address_address_id" varchar(255), "company_company_id" varchar(255), primary key ("company_address_id"));
create table "company_contract_company" ("company_contract_company_id" varchar(255) not null, "company_id1" varchar(255), "company_id2" varchar(255), "contract_id" varchar(255), "company1_company_id" varchar(255), "company2_company_id" varchar(255), "contract_contract_id" varchar(255), primary key ("company_contract_company_id"));
create table "company_contract_person" ("company_contract_person_id" varchar(255) not null, "app_user_id" varchar(255), "company_id" varchar(255), "contract_id" varchar(255), "app_user_app_user_id" varchar(255), "company_company_id" varchar(255), "contract_contract_id" varchar(255), primary key ("company_contract_person_id"));
create table "company_wallet" ("company_wallet_id" varchar(255) not null, "company_id" varchar(255), "wallet_id" varchar(255), "company_company_id" varchar(255), "wallet_wallet_id" varchar(255), primary key ("company_wallet_id"));
create table "contract" ("contract_id" varchar(255) not null, "contract_code" varchar(255), "description" varchar(255), primary key ("contract_id"));
create table "country" ("country_id" varchar(255) not null, "name" varchar(255), primary key ("country_id"));
create table "county" ("county_id" varchar(255) not null, "code" varchar(255), "country_id" varchar(255), "name" varchar(255), "country_country_id" varchar(255), primary key ("county_id"));
create table "person" ("person_id" varchar(255) not null, "app_user_id" varchar(255), "birth_date" varchar(255), "company_id" varchar(255), "firstname" varchar(255), "gender" varchar(255), "lastname" varchar(255), "salutation" varchar(255), "app_user_app_user_id" varchar(255), "company_company_id" varchar(255), primary key ("person_id"));
create table "person_address" ("person_address_id" varchar(255) not null, "address_id" varchar(255), "person_id" varchar(255), "address_address_id" varchar(255), "person_person_id" varchar(255), primary key ("person_address_id"));
create table "person_wallet" ("person_wallet_id" varchar(255) not null, "app_user_id" varchar(255), "wallet_id" varchar(255), "app_user_app_user_id" varchar(255), "wallet_wallet_id" varchar(255), primary key ("person_wallet_id"));
create table "role_type" ("role_type_id" varchar(255) not null, "description" varchar(255), "role_code" varchar(255), primary key ("role_type_id"));
create table "user_role" ("user_role_id" varchar(255) not null, "app_user_id" varchar(255), "company_id" varchar(255), "role_type_id" varchar(255), "app_user_app_user_id" varchar(255), "company_company_id" varchar(255), "role_type_role_type_id" varchar(255), primary key ("user_role_id"));
create table "wallet" ("wallet_id" varchar(255) not null, "wallet_code" varchar(255), primary key ("wallet_id"));
alter table "address" add constraint FK40bqcj6bjsb98lk42k5xu1ofs foreign key ("city_city_id") references "city";
alter table "city" add constraint FKfcux7khuhbm1ueu1e2gchwt3v foreign key ("country_country_id") references "country";
alter table "city" add constraint FKe5mjxjkv8937am2w8p732ndtt foreign key ("county_county_id") references "county";
alter table "company_address" add constraint FK57d4qgd2n298lnb77yug00ji5 foreign key ("address_address_id") references "address";
alter table "company_address" add constraint FKhv1l6jjnpa36h6hsxj4n38u1q foreign key ("company_company_id") references "company";
alter table "company_contract_company" add constraint FKq66m338x2l1249ofg450gn6cr foreign key ("company1_company_id") references "company";
alter table "company_contract_company" add constraint FKsu5aikh6v5192gk500uvf8ns2 foreign key ("company2_company_id") references "company";
alter table "company_contract_company" add constraint FKiyn18cofrqscxyv59qdjdf099 foreign key ("contract_contract_id") references "contract";
alter table "company_contract_person" add constraint FK22xal0967p6y6gqu8bmk5uca4 foreign key ("app_user_app_user_id") references "app_user";
alter table "company_contract_person" add constraint FKhbfed95w9vfsuo1qmgoc9epk8 foreign key ("company_company_id") references "company";
alter table "company_contract_person" add constraint FK8h6cm6ow0h7v5sv9bpo36dyme foreign key ("contract_contract_id") references "contract";
alter table "company_wallet" add constraint FKaw1fc6m2qagtgfx7p242yxty8 foreign key ("company_company_id") references "company";
alter table "company_wallet" add constraint FKg53fg2pcqbeej51y7hisusak9 foreign key ("wallet_wallet_id") references "wallet";
alter table "county" add constraint FKaj87p4kvbe337ogg59kxu0h78 foreign key ("country_country_id") references "country";
alter table "person" add constraint FKnjxrac42o6ba9bsfubqpksdey foreign key ("app_user_app_user_id") references "app_user";
alter table "person" add constraint FK5os9h6ehska5jc5s1ulnufy89 foreign key ("company_company_id") references "company";
alter table "person_address" add constraint FKi4plkhoi0niqggkd8ootjurll foreign key ("address_address_id") references "address";
alter table "person_address" add constraint FKx0hv1r2xxl5gxk56iph6h8of foreign key ("person_person_id") references "person";
alter table "person_wallet" add constraint FKixfjaff5lqaxjb13megmfbnip foreign key ("app_user_app_user_id") references "app_user";
alter table "person_wallet" add constraint FKt049or4vu7495vpomxxlw4yi6 foreign key ("wallet_wallet_id") references "wallet";
alter table "user_role" add constraint FKl0kbc1e27rjb0k58vt21or5xt foreign key ("app_user_app_user_id") references "app_user";
alter table "user_role" add constraint FKdohhty692laxweri97mxmjv84 foreign key ("company_company_id") references "company";
alter table "user_role" add constraint FKkrh6fgpuexoj6u55yie2e28fs foreign key ("role_type_role_type_id") references "role_type";
create table "address" ("address_id" varchar(255) not null, "apt_number" int4, "building" varchar(255), "city_id" varchar(255), "county_id" varchar(255), "floor" int4, "number" varchar(255), "post_code" varchar(255), "street" varchar(255), "city_city_id" varchar(255), primary key ("address_id"));
create table "app_user" ("app_user_id" varchar(255) not null, "email" varchar(255), "password" varchar(255), "username" varchar(255), primary key ("app_user_id"));
create table "city" ("city_id" varchar(255) not null, "country_id" varchar(255), "county_id" varchar(255), "name" varchar(255), "country_country_id" varchar(255), "county_county_id" varchar(255), primary key ("city_id"));
create table "company" ("company_id" varchar(255) not null, "cui" varchar(255), "description" varchar(255), "name" varchar(255), "reg_identifier" varchar(255), primary key ("company_id"));
create table "company_address" ("company_address_id" varchar(255) not null, "address_id" varchar(255), "company_id" varchar(255), "address_address_id" varchar(255), "company_company_id" varchar(255), primary key ("company_address_id"));
create table "company_contract_company" ("company_contract_company_id" varchar(255) not null, "company_id1" varchar(255), "company_id2" varchar(255), "contract_id" varchar(255), "company1_company_id" varchar(255), "company2_company_id" varchar(255), "contract_contract_id" varchar(255), primary key ("company_contract_company_id"));
create table "company_contract_person" ("company_contract_person_id" varchar(255) not null, "app_user_id" varchar(255), "company_id" varchar(255), "contract_id" varchar(255), "app_user_app_user_id" varchar(255), "company_company_id" varchar(255), "contract_contract_id" varchar(255), primary key ("company_contract_person_id"));
create table "company_wallet" ("company_wallet_id" varchar(255) not null, "company_id" varchar(255), "wallet_id" varchar(255), "company_company_id" varchar(255), "wallet_wallet_id" varchar(255), primary key ("company_wallet_id"));
create table "contract" ("contract_id" varchar(255) not null, "contract_code" varchar(255), "description" varchar(255), primary key ("contract_id"));
create table "country" ("country_id" varchar(255) not null, "name" varchar(255), primary key ("country_id"));
create table "county" ("county_id" varchar(255) not null, "code" varchar(255), "country_id" varchar(255), "name" varchar(255), "country_country_id" varchar(255), primary key ("county_id"));
create table "person" ("person_id" varchar(255) not null, "app_user_id" varchar(255), "birth_date" varchar(255), "company_id" varchar(255), "firstname" varchar(255), "gender" varchar(255), "lastname" varchar(255), "salutation" varchar(255), "app_user_app_user_id" varchar(255), "company_company_id" varchar(255), primary key ("person_id"));
create table "person_address" ("person_address_id" varchar(255) not null, "address_id" varchar(255), "person_id" varchar(255), "address_address_id" varchar(255), "person_person_id" varchar(255), primary key ("person_address_id"));
create table "person_wallet" ("person_wallet_id" varchar(255) not null, "app_user_id" varchar(255), "wallet_id" varchar(255), "app_user_app_user_id" varchar(255), "wallet_wallet_id" varchar(255), primary key ("person_wallet_id"));
create table "role_type" ("role_type_id" varchar(255) not null, "description" varchar(255), "role_code" varchar(255), primary key ("role_type_id"));
create table "user_role" ("user_role_id" varchar(255) not null, "app_user_id" varchar(255), "company_id" varchar(255), "role_type_id" varchar(255), "app_user_app_user_id" varchar(255), "company_company_id" varchar(255), "role_type_role_type_id" varchar(255), primary key ("user_role_id"));
create table "wallet" ("wallet_id" varchar(255) not null, "wallet_code" varchar(255), primary key ("wallet_id"));
alter table "address" add constraint FK40bqcj6bjsb98lk42k5xu1ofs foreign key ("city_city_id") references "city";
alter table "city" add constraint FKfcux7khuhbm1ueu1e2gchwt3v foreign key ("country_country_id") references "country";
alter table "city" add constraint FKe5mjxjkv8937am2w8p732ndtt foreign key ("county_county_id") references "county";
alter table "company_address" add constraint FK57d4qgd2n298lnb77yug00ji5 foreign key ("address_address_id") references "address";
alter table "company_address" add constraint FKhv1l6jjnpa36h6hsxj4n38u1q foreign key ("company_company_id") references "company";
alter table "company_contract_company" add constraint FKq66m338x2l1249ofg450gn6cr foreign key ("company1_company_id") references "company";
alter table "company_contract_company" add constraint FKsu5aikh6v5192gk500uvf8ns2 foreign key ("company2_company_id") references "company";
alter table "company_contract_company" add constraint FKiyn18cofrqscxyv59qdjdf099 foreign key ("contract_contract_id") references "contract";
alter table "company_contract_person" add constraint FK22xal0967p6y6gqu8bmk5uca4 foreign key ("app_user_app_user_id") references "app_user";
alter table "company_contract_person" add constraint FKhbfed95w9vfsuo1qmgoc9epk8 foreign key ("company_company_id") references "company";
alter table "company_contract_person" add constraint FK8h6cm6ow0h7v5sv9bpo36dyme foreign key ("contract_contract_id") references "contract";
alter table "company_wallet" add constraint FKaw1fc6m2qagtgfx7p242yxty8 foreign key ("company_company_id") references "company";
alter table "company_wallet" add constraint FKg53fg2pcqbeej51y7hisusak9 foreign key ("wallet_wallet_id") references "wallet";
alter table "county" add constraint FKaj87p4kvbe337ogg59kxu0h78 foreign key ("country_country_id") references "country";
alter table "person" add constraint FKnjxrac42o6ba9bsfubqpksdey foreign key ("app_user_app_user_id") references "app_user";
alter table "person" add constraint FK5os9h6ehska5jc5s1ulnufy89 foreign key ("company_company_id") references "company";
alter table "person_address" add constraint FKi4plkhoi0niqggkd8ootjurll foreign key ("address_address_id") references "address";
alter table "person_address" add constraint FKx0hv1r2xxl5gxk56iph6h8of foreign key ("person_person_id") references "person";
alter table "person_wallet" add constraint FKixfjaff5lqaxjb13megmfbnip foreign key ("app_user_app_user_id") references "app_user";
alter table "person_wallet" add constraint FKt049or4vu7495vpomxxlw4yi6 foreign key ("wallet_wallet_id") references "wallet";
alter table "user_role" add constraint FKl0kbc1e27rjb0k58vt21or5xt foreign key ("app_user_app_user_id") references "app_user";
alter table "user_role" add constraint FKdohhty692laxweri97mxmjv84 foreign key ("company_company_id") references "company";
alter table "user_role" add constraint FKkrh6fgpuexoj6u55yie2e28fs foreign key ("role_type_role_type_id") references "role_type";
create table "address" ("address_id" varchar(255) not null, "apt_number" int4, "building" varchar(255), "city_id" varchar(255), "county_id" varchar(255), "floor" int4, "number" varchar(255), "post_code" varchar(255), "street" varchar(255), "city_city_id" varchar(255), primary key ("address_id"));
create table "app_user" ("app_user_id" varchar(255) not null, "email" varchar(255), "password" varchar(255), "username" varchar(255), primary key ("app_user_id"));
create table "city" ("city_id" varchar(255) not null, "country_id" varchar(255), "county_id" varchar(255), "name" varchar(255), "country_country_id" varchar(255), "county_county_id" varchar(255), primary key ("city_id"));
create table "company" ("company_id" varchar(255) not null, "cui" varchar(255), "description" varchar(255), "name" varchar(255), "reg_identifier" varchar(255), primary key ("company_id"));
create table "company_address" ("company_address_id" varchar(255) not null, "address_id" varchar(255), "company_id" varchar(255), "address_address_id" varchar(255), "company_company_id" varchar(255), primary key ("company_address_id"));
create table "company_contract_company" ("company_contract_company_id" varchar(255) not null, "company_id1" varchar(255), "company_id2" varchar(255), "contract_id" varchar(255), "company1_company_id" varchar(255), "company2_company_id" varchar(255), "contract_contract_id" varchar(255), primary key ("company_contract_company_id"));
create table "company_contract_person" ("company_contract_person_id" varchar(255) not null, "app_user_id" varchar(255), "company_id" varchar(255), "contract_id" varchar(255), "app_user_app_user_id" varchar(255), "company_company_id" varchar(255), "contract_contract_id" varchar(255), primary key ("company_contract_person_id"));
create table "company_wallet" ("company_wallet_id" varchar(255) not null, "company_id" varchar(255), "wallet_id" varchar(255), "company_company_id" varchar(255), "wallet_wallet_id" varchar(255), primary key ("company_wallet_id"));
create table "contract" ("contract_id" varchar(255) not null, "contract_code" varchar(255), "description" varchar(255), primary key ("contract_id"));
create table "country" ("country_id" varchar(255) not null, "name" varchar(255), primary key ("country_id"));
create table "county" ("county_id" varchar(255) not null, "code" varchar(255), "country_id" varchar(255), "name" varchar(255), "country_country_id" varchar(255), primary key ("county_id"));
create table "person" ("person_id" varchar(255) not null, "app_user_id" varchar(255), "birth_date" varchar(255), "company_id" varchar(255), "firstname" varchar(255), "gender" varchar(255), "lastname" varchar(255), "salutation" varchar(255), "app_user_app_user_id" varchar(255), "company_company_id" varchar(255), primary key ("person_id"));
create table "person_address" ("person_address_id" varchar(255) not null, "address_id" varchar(255), "person_id" varchar(255), "address_address_id" varchar(255), "person_person_id" varchar(255), primary key ("person_address_id"));
create table "person_wallet" ("person_wallet_id" varchar(255) not null, "app_user_id" varchar(255), "wallet_id" varchar(255), "app_user_app_user_id" varchar(255), "wallet_wallet_id" varchar(255), primary key ("person_wallet_id"));
create table "role_type" ("role_type_id" varchar(255) not null, "description" varchar(255), "role_code" varchar(255), primary key ("role_type_id"));
create table "user_role" ("user_role_id" varchar(255) not null, "app_user_id" varchar(255), "company_id" varchar(255), "role_type_id" varchar(255), "app_user_app_user_id" varchar(255), "company_company_id" varchar(255), "role_type_role_type_id" varchar(255), primary key ("user_role_id"));
create table "wallet" ("wallet_id" varchar(255) not null, "wallet_code" varchar(255), primary key ("wallet_id"));
alter table "address" add constraint FK40bqcj6bjsb98lk42k5xu1ofs foreign key ("city_city_id") references "city";
alter table "city" add constraint FKfcux7khuhbm1ueu1e2gchwt3v foreign key ("country_country_id") references "country";
alter table "city" add constraint FKe5mjxjkv8937am2w8p732ndtt foreign key ("county_county_id") references "county";
alter table "company_address" add constraint FK57d4qgd2n298lnb77yug00ji5 foreign key ("address_address_id") references "address";
alter table "company_address" add constraint FKhv1l6jjnpa36h6hsxj4n38u1q foreign key ("company_company_id") references "company";
alter table "company_contract_company" add constraint FKq66m338x2l1249ofg450gn6cr foreign key ("company1_company_id") references "company";
alter table "company_contract_company" add constraint FKsu5aikh6v5192gk500uvf8ns2 foreign key ("company2_company_id") references "company";
alter table "company_contract_company" add constraint FKiyn18cofrqscxyv59qdjdf099 foreign key ("contract_contract_id") references "contract";
alter table "company_contract_person" add constraint FK22xal0967p6y6gqu8bmk5uca4 foreign key ("app_user_app_user_id") references "app_user";
alter table "company_contract_person" add constraint FKhbfed95w9vfsuo1qmgoc9epk8 foreign key ("company_company_id") references "company";
alter table "company_contract_person" add constraint FK8h6cm6ow0h7v5sv9bpo36dyme foreign key ("contract_contract_id") references "contract";
alter table "company_wallet" add constraint FKaw1fc6m2qagtgfx7p242yxty8 foreign key ("company_company_id") references "company";
alter table "company_wallet" add constraint FKg53fg2pcqbeej51y7hisusak9 foreign key ("wallet_wallet_id") references "wallet";
alter table "county" add constraint FKaj87p4kvbe337ogg59kxu0h78 foreign key ("country_country_id") references "country";
alter table "person" add constraint FKnjxrac42o6ba9bsfubqpksdey foreign key ("app_user_app_user_id") references "app_user";
alter table "person" add constraint FK5os9h6ehska5jc5s1ulnufy89 foreign key ("company_company_id") references "company";
alter table "person_address" add constraint FKi4plkhoi0niqggkd8ootjurll foreign key ("address_address_id") references "address";
alter table "person_address" add constraint FKx0hv1r2xxl5gxk56iph6h8of foreign key ("person_person_id") references "person";
alter table "person_wallet" add constraint FKixfjaff5lqaxjb13megmfbnip foreign key ("app_user_app_user_id") references "app_user";
alter table "person_wallet" add constraint FKt049or4vu7495vpomxxlw4yi6 foreign key ("wallet_wallet_id") references "wallet";
alter table "user_role" add constraint FKl0kbc1e27rjb0k58vt21or5xt foreign key ("app_user_app_user_id") references "app_user";
alter table "user_role" add constraint FKdohhty692laxweri97mxmjv84 foreign key ("company_company_id") references "company";
alter table "user_role" add constraint FKkrh6fgpuexoj6u55yie2e28fs foreign key ("role_type_role_type_id") references "role_type";
create table "address" ("address_id" varchar(255) not null, "apt_number" int4, "building" varchar(255), "city_id" varchar(255), "county_id" varchar(255), "floor" int4, "number" varchar(255), "post_code" varchar(255), "street" varchar(255), "city_city_id" varchar(255), primary key ("address_id"));
create table "app_user" ("app_user_id" varchar(255) not null, "email" varchar(255), "password" varchar(255), "username" varchar(255), primary key ("app_user_id"));
create table "city" ("city_id" varchar(255) not null, "country_id" varchar(255), "county_id" varchar(255), "name" varchar(255), "country_country_id" varchar(255), "county_county_id" varchar(255), primary key ("city_id"));
create table "company" ("company_id" varchar(255) not null, "cui" varchar(255), "description" varchar(255), "name" varchar(255), "reg_identifier" varchar(255), primary key ("company_id"));
create table "company_address" ("company_address_id" varchar(255) not null, "address_id" varchar(255), "company_id" varchar(255), "address_address_id" varchar(255), "company_company_id" varchar(255), primary key ("company_address_id"));
create table "company_contract_company" ("company_contract_company_id" varchar(255) not null, "company_id1" varchar(255), "company_id2" varchar(255), "contract_id" varchar(255), "company1_company_id" varchar(255), "company2_company_id" varchar(255), "contract_contract_id" varchar(255), primary key ("company_contract_company_id"));
create table "company_contract_person" ("company_contract_person_id" varchar(255) not null, "app_user_id" varchar(255), "company_id" varchar(255), "contract_id" varchar(255), "app_user_app_user_id" varchar(255), "company_company_id" varchar(255), "contract_contract_id" varchar(255), primary key ("company_contract_person_id"));
create table "company_wallet" ("company_wallet_id" varchar(255) not null, "company_id" varchar(255), "wallet_id" varchar(255), "company_company_id" varchar(255), "wallet_wallet_id" varchar(255), primary key ("company_wallet_id"));
create table "contract" ("contract_id" varchar(255) not null, "contract_code" varchar(255), "description" varchar(255), primary key ("contract_id"));
create table "country" ("country_id" varchar(255) not null, "name" varchar(255), primary key ("country_id"));
create table "county" ("county_id" varchar(255) not null, "code" varchar(255), "country_id" varchar(255), "name" varchar(255), "country_country_id" varchar(255), primary key ("county_id"));
create table "person" ("person_id" varchar(255) not null, "app_user_id" varchar(255), "birth_date" varchar(255), "company_id" varchar(255), "firstname" varchar(255), "gender" varchar(255), "lastname" varchar(255), "salutation" varchar(255), "app_user_app_user_id" varchar(255), "company_company_id" varchar(255), primary key ("person_id"));
create table "person_address" ("person_address_id" varchar(255) not null, "address_id" varchar(255), "person_id" varchar(255), "address_address_id" varchar(255), "person_person_id" varchar(255), primary key ("person_address_id"));
create table "person_wallet" ("person_wallet_id" varchar(255) not null, "app_user_id" varchar(255), "wallet_id" varchar(255), "app_user_app_user_id" varchar(255), "wallet_wallet_id" varchar(255), primary key ("person_wallet_id"));
create table "role_type" ("role_type_id" varchar(255) not null, "description" varchar(255), "role_code" varchar(255), primary key ("role_type_id"));
create table "user_role" ("user_role_id" varchar(255) not null, "app_user_id" varchar(255), "company_id" varchar(255), "role_type_id" varchar(255), "app_user_app_user_id" varchar(255), "company_company_id" varchar(255), "role_type_role_type_id" varchar(255), primary key ("user_role_id"));
create table "wallet" ("wallet_id" varchar(255) not null, "wallet_code" varchar(255), primary key ("wallet_id"));
alter table "address" add constraint FK40bqcj6bjsb98lk42k5xu1ofs foreign key ("city_city_id") references "city";
alter table "city" add constraint FKfcux7khuhbm1ueu1e2gchwt3v foreign key ("country_country_id") references "country";
alter table "city" add constraint FKe5mjxjkv8937am2w8p732ndtt foreign key ("county_county_id") references "county";
alter table "company_address" add constraint FK57d4qgd2n298lnb77yug00ji5 foreign key ("address_address_id") references "address";
alter table "company_address" add constraint FKhv1l6jjnpa36h6hsxj4n38u1q foreign key ("company_company_id") references "company";
alter table "company_contract_company" add constraint FKq66m338x2l1249ofg450gn6cr foreign key ("company1_company_id") references "company";
alter table "company_contract_company" add constraint FKsu5aikh6v5192gk500uvf8ns2 foreign key ("company2_company_id") references "company";
alter table "company_contract_company" add constraint FKiyn18cofrqscxyv59qdjdf099 foreign key ("contract_contract_id") references "contract";
alter table "company_contract_person" add constraint FK22xal0967p6y6gqu8bmk5uca4 foreign key ("app_user_app_user_id") references "app_user";
alter table "company_contract_person" add constraint FKhbfed95w9vfsuo1qmgoc9epk8 foreign key ("company_company_id") references "company";
alter table "company_contract_person" add constraint FK8h6cm6ow0h7v5sv9bpo36dyme foreign key ("contract_contract_id") references "contract";
alter table "company_wallet" add constraint FKaw1fc6m2qagtgfx7p242yxty8 foreign key ("company_company_id") references "company";
alter table "company_wallet" add constraint FKg53fg2pcqbeej51y7hisusak9 foreign key ("wallet_wallet_id") references "wallet";
alter table "county" add constraint FKaj87p4kvbe337ogg59kxu0h78 foreign key ("country_country_id") references "country";
alter table "person" add constraint FKnjxrac42o6ba9bsfubqpksdey foreign key ("app_user_app_user_id") references "app_user";
alter table "person" add constraint FK5os9h6ehska5jc5s1ulnufy89 foreign key ("company_company_id") references "company";
alter table "person_address" add constraint FKi4plkhoi0niqggkd8ootjurll foreign key ("address_address_id") references "address";
alter table "person_address" add constraint FKx0hv1r2xxl5gxk56iph6h8of foreign key ("person_person_id") references "person";
alter table "person_wallet" add constraint FKixfjaff5lqaxjb13megmfbnip foreign key ("app_user_app_user_id") references "app_user";
alter table "person_wallet" add constraint FKt049or4vu7495vpomxxlw4yi6 foreign key ("wallet_wallet_id") references "wallet";
alter table "user_role" add constraint FKl0kbc1e27rjb0k58vt21or5xt foreign key ("app_user_app_user_id") references "app_user";
alter table "user_role" add constraint FKdohhty692laxweri97mxmjv84 foreign key ("company_company_id") references "company";
alter table "user_role" add constraint FKkrh6fgpuexoj6u55yie2e28fs foreign key ("role_type_role_type_id") references "role_type";
