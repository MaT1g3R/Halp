from faker import Faker
from faker.providers import internet, lorem, misc, person

fake = Faker()
fake.add_provider(internet)
fake.add_provider(misc)
fake.add_provider(person)
fake.add_provider(lorem)
