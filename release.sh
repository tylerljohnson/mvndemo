#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

# Variables
FEATURE_BRANCH=$1
RELEASE_VERSION=$2
NEXT_SNAPSHOT_VERSION=$3

if [ -z "$FEATURE_BRANCH" ] || [ -z "$RELEASE_VERSION" ] || [ -z "$NEXT_SNAPSHOT_VERSION" ]; then
  echo "Usage: $0 <feature-branch> <release-version> <next-snapshot-version>"
  exit 1
fi

# Checkout main branch and merge feature branch
git checkout main
git pull origin main
git merge --no-ff $FEATURE_BRANCH
git push origin main

# Delete the feature branch locally and remotely
git branch -d $FEATURE_BRANCH
git push origin --delete $FEATURE_BRANCH

# Create a release branch
RELEASE_BRANCH="release/$RELEASE_VERSION"
git checkout -b $RELEASE_BRANCH

# Update version to release version in pom.xml
mvn versions:set -DnewVersion=$RELEASE_VERSION
git add pom.xml
git commit -m "Update version to $RELEASE_VERSION"
git push origin $RELEASE_BRANCH

# Run Maven release process
mvn release:prepare -Dresume=false -DreleaseVersion=$RELEASE_VERSION -DdevelopmentVersion=$NEXT_SNAPSHOT_VERSION
mvn release:perform

# Merge the release branch back into main
git checkout main
git pull origin main
git merge --no-ff $RELEASE_BRANCH
git push origin main

# Delete the release branch locally and remotely
git branch -d $RELEASE_BRANCH
git push origin --delete $RELEASE_BRANCH

# Ensure main branch has the next snapshot version
mvn versions:set -DnewVersion=$NEXT_SNAPSHOT_VERSION
git add pom.xml
git commit -m "Update version to $NEXT_SNAPSHOT_VERSION"
git push origin main

echo "Release process completed successfully."

