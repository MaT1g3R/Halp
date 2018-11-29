from faker import Faker
from faker.providers import date_time, geo, internet, lorem, misc, person

fake = Faker()
fake.add_provider(internet)
fake.add_provider(misc)
fake.add_provider(person)
fake.add_provider(lorem)
fake.add_provider(date_time)
fake.add_provider(geo)
