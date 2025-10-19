from pymongo import MongoClient
from bson.objectid import ObjectId
import os

class AnimalShelter(object):
    """ CRUD operations for Animal collection in MongoDB """

    def __init__(self, db="AAC", col="animals"):
        #USER = os.getenv("MONGO_USER", "aacuser") #GetEnv to help protect sensitive code
        #PASS = os.getenv("MONGO_PASS", "snhu2025")
        #HOST = os.getenv("MONGO_HOST", "nv-desktop-services.apporto.com")
        #PORT = int(os.getenv("MONGO_PORT", 31658))

        #self.client = MongoClient(f"mongodb://{USER}:{PASS}@{HOST}:{PORT}")
        self.client = MongoClient("mongodb://127.0.0.1:27017")
        self.database = self.client[db]
        self.collection = self.database[col]

    # ---------- CRUD METHODS ----------

    def create(self, data: dict):
        if not isinstance(data, dict) or not data: # Verifies that the entry is a dictionary/data entry
            raise ValueError("Data Must be a Non-Empty Dictionary")
        return str(self.collection.insert_one(data).inserted_id)

    def read(self, query: dict = None):
        query = query or {} # Checks if the query is valid
        return list(self.collection.find(query)) # Returns list of valid query

    def update(self, query: dict, new_values: dict):
        if not query or not new_values: #Checking for a valid query and new values, otherwise error
            raise ValueError("Update Requires Query Entry and New Values")
        result = self.collection.update_many(query, {"$set": new_values}) #Updates entries
        return {"matched": result.matched_count, "modified": result.modified_count} #Displays the found existing entries and modified count

    def delete(self, query: dict):
        if not query:
            raise ValueError("Delete Requires a Query for Security Purposes") #Helps prevent the wiping of entire Database
        result = self.collection.delete_many(query) # Deletes as many entries that match the query
        return {"deleted": result.deleted_count}

# Queries to enter into notebook

# Step 1: Connect to the shelter database
#from AnimalShelterCRUD import AnimalShelter
#shelter = AnimalShelter()

# Step 2: Create a record
#new_id = shelter.create({"name": "Fluffy", "animal_type": "Dog"})
#print("Created:", new_id)

# Step 3: Read it back
#dogs = shelter.read({"animal_type": "Dog"})
#print("Read:", dogs)

# Step 4: Update the record
#update_result = shelter.update({"name": "Fluffy"}, {"animal_type": "Cat"})
#print("Update:", update_result)

# Step 5: Delete the record
#delete_result = shelter.delete({"name": "Fluffy"})
#print("Delete:", delete_result)