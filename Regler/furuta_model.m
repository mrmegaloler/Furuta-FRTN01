%%% Futura pendulum model parameters (see Åkesson (1999)) (updated 2020-04-06)
  
l = 0.413;
M = 0.01;
Jp = 0.0009;
r = 0.235;
J = 0.05;
m = 0.02;
g = 9.81;

sampling_time = 0.01;

alfa = Jp+M*l^2;
beta = J+M*r^2+m*r^2;
gamma = M*r*l;
epsilon = l*g*(M+m/2);

friction = 0.2;  % Added viscous friction compared to Åkesson (1999)

%%% Models for control design, state vector x = [theta; thetaDot; phi; phiDot]

% Linear model for control in the upper position

%declaration of q and r for lqr, alternative 2
Q = diag([100, 20, 40, 20])

R = [5] ;

A_upper = [-friction 1 0 0;
     (beta*epsilon)/(alfa*beta-gamma^2) 0 0 0;
     0 0 -friction 1;
     -(gamma*epsilon)/(alfa*beta-gamma^2) 0 0 0];
B_upper = [0 -gamma/(alfa*beta-gamma^2)*g 0 alfa/(alfa*beta-gamma^2)*g]';

% Linear model for control in lower position

A_lower = [-friction 1 0 0;
     -(beta*epsilon)/(alfa*beta-gamma^2) 0 0 0;
     0 0 -friction 1;
     -(gamma*epsilon)/(alfa*beta-gamma^2) 0 0 0];
B_lower = [0 gamma/(alfa*beta-gamma^2)*g 0 alfa/(alfa*beta-gamma^2)*g]';

%code for generating the lqr filter
[K,S,E] = lqrd(A_lower,B_lower,Q,R,sampling_time)

%code to check the poles generated
%g = zpk(1,[E(1,1),E(2,1),E(3,1),E(4,1)],1,0.01);

%pole(d2c(g));

%code for generating by pole placement, alternative 1

[A,B] = c2d(A_upper,B_upper,0.001);

p1_l = exp((-6*cosd(20)+6*sind(20)*i)*sampling_time);
p2_l = exp((-6*cosd(20)-6*sind(20)*i)*sampling_time);
p3_l = exp(-22*sampling_time);
p4_l = exp(-1*sampling_time);

%[K, PREC,message] = place(A,B,[p1_l,p2_l,p3_l,p4_l])

sys = c2d(ss(A_upper,B_upper,diag([1,1,1,1]), 0), 0.05);
sys_feedback = feedback(sys, K);
%step(sys_feedback)
initial(sys_feedback,[0.2,0,0,0])

[M,P,Z,E] = lqed(A_upper,diag([1,1,1,1]),[1, 0, 1, 0],diag([1, 1, 1, 1]),10,sampling_time)





