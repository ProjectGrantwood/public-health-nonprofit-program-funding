-- Data for table 'program'
INSERT INTO program (program_id, program_name, program_budget, program_budget_percentage_grant_funded, program_budget_percentage_donation_funded) VALUES (1, 'Flu Vaccine Clinic', 30000, 1.0, 0.0);
INSERT INTO program (program_id, program_name, program_budget, program_budget_percentage_grant_funded, program_budget_percentage_donation_funded) VALUES (2, 'Pre-Med Scholarship', 100000, 0.0, 1.0);
INSERT INTO program (program_id, program_name, program_budget, program_budget_percentage_grant_funded, program_budget_percentage_donation_funded) VALUES (3, 'Career Exploration Camp', 15000, 0.3, 0.7);
INSERT INTO program (program_id, program_name, program_budget, program_budget_percentage_grant_funded, program_budget_percentage_donation_funded) VALUES (4, 'Continuing Education For Providers on Cultural Competency', 5000, 0.9, 0.1);
INSERT INTO program (program_id, program_name, program_budget, program_budget_percentage_grant_funded, program_budget_percentage_donation_funded) VALUES (5, 'Lupus Awareness', 20000, 0.2, 0.8);
INSERT INTO program (program_id, program_name, program_budget, program_budget_percentage_grant_funded, program_budget_percentage_donation_funded) VALUES (6, 'Elderly Care', 100000, 0.7, 0.3);

-- Data for table 'donor'
INSERT INTO donor (donor_id, donor_name, donor_address, donor_phone, donor_email, donor_affiliation) VALUES (1, 'Jane Doe', '456 Main St, Anytown, USA', '123-456-7890', 'DoeJane@yahoo.com', 'Individual');
INSERT INTO donor (donor_id, donor_name, donor_address, donor_phone, donor_email, donor_affiliation) VALUES (2, 'John Smith', '123 Main St, Anytown, USA', '123-456-7890', 'JohnSmithDonor@gmail.com', 'Individual');
INSERT INTO donor (donor_id, donor_name, donor_address, donor_phone, donor_email, donor_affiliation) VALUES (3, 'ABC Company', '789 Main St, Anytown, USA', '123-456-7890', 'philanthropy@abccompany.com', 'Corporate');
INSERT INTO donor (donor_id, donor_name, donor_address, donor_phone, donor_email, donor_affiliation) VALUES (4, 'XYZ Company', '987 Main St, Anytown, USA', '123-456-7890', 'giving@xyzcompany.com', 'Corporate');
INSERT INTO donor (donor_id, donor_name, donor_address, donor_phone, donor_email, donor_affiliation) VALUES (5, 'Community Foundation', '654 Main St, Anytown, USA', '123-456-7890', 'treasurer@comfound.org', 'Foundation');
INSERT INTO donor (donor_id, donor_name, donor_address, donor_phone, donor_email, donor_affiliation) VALUES (6, 'Wellbetter Local', '654 Main St, Anytown, USA', '123-456-7890', 'giving@wellbetter.org', 'Foundation');

-- Data for table 'donation'


--Data for table 'granting_org'
INSERT INTO granting_org (granting_org_id, granting_org_name, granting_org_contact_name, granting_org_contact_email, granting_org_contact_phone, granting_org_type) VALUES (1, 'ABC Foundation', 'Donnie Darko', 'donald.darko@abcfoundation.org', '123-456-7890', 'PRIVATE');
INSERT INTO granting_org (granting_org_id, granting_org_name, granting_org_contact_name, granting_org_contact_email, granting_org_contact_phone, granting_org_type) VALUES (2, 'XYZ Foundation', 'Mr. Bean', 'mr.bean@xyzfoundation.org', '987-654-3210', 'PRIVATE');
INSERT INTO granting_org (granting_org_id, granting_org_name, granting_org_contact_name, granting_org_contact_email, granting_org_contact_phone, granting_org_type) VALUES (3, 'Community Giving Group', 'Gene Eugene', 'g.eugene@cgg.gov', '321-654-9870', 'FEDERAL');
INSERT INTO granting_org (granting_org_id, granting_org_name, granting_org_contact_name, granting_org_contact_email, granting_org_contact_phone, granting_org_type) VALUES (4, 'Wellbetter State', 'Sally Smith', 'sally.smith@wbstate.gov', '456-789-0123', 'FEDERAL');


-- Data for table 'financial_grant'