fnames = dir('*.png');
numfids = length(fnames);
for K = 1:numfids
I = imread(fnames(K).name);
J = entropy(I);
fprintf('for file %s, entropy %f\n',fnames(K).name,J);
end